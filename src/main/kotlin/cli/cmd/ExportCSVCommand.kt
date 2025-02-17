package com.arnyminerz.ggs.cli.cmd

import com.arnyminerz.ggs.cli.args.CLIArgument
import com.arnyminerz.ggs.cli.args.Database
import com.arnyminerz.ggs.cli.args.Help
import com.arnyminerz.ggs.cli.args.Metadata
import com.arnyminerz.ggs.cli.args.Output
import com.arnyminerz.ggs.csv
import com.arnyminerz.ggs.data.Activity
import com.arnyminerz.ggs.data.MetaData
import com.arnyminerz.ggs.gadgetbridge.Gadgetbridge
import com.arnyminerz.ggs.json
import java.io.File
import java.time.LocalDate
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.decodeFromStream

@OptIn(ExperimentalSerializationApi::class)
object ExportCSVCommand : Command("export_csv") {
    override val arguments = listOf<CLIArgument>(Help, Database, Metadata)

    override fun invoke(arguments: MutableMap<CLIArgument, String?>) {
        if (arguments[Help] != null) {
            println("Usage:")
            println("$name --database <database> --metadata [metadata.json] --output [output.csv]")
            return
        }

        val dbFilePath = arguments[Database]
        requireNotNull(dbFilePath) { "Database file not provided.\nUsage: --database <database>" }

        val metadataFilePath = arguments[Metadata] ?: "metadata.json"
        val metadataFile = File(metadataFilePath)

        val outputFilePath = arguments[Output] ?: "output.csv"
        val outputFile = File(outputFilePath)

        val dbFile = File(dbFilePath)
        check(dbFile.exists()) { "Database file doesn't exist." }
        Gadgetbridge.connect(dbFile)

        val metadata = metadataFile.takeIf { it.exists() }?.inputStream()?.use {
            json.decodeFromStream(MetaData.serializer(), it)
        } ?: MetaData()

        val activities = Gadgetbridge.getActivities(metadata.days)
        println("Found ${activities.size} activities")

        if (activities.isEmpty()) {
            println("No new activities were found.")
            return
        }

        println("Serializing...")
        csv.encodeToString(ListSerializer(Activity.serializer()), activities).let { data ->
            val content = "Activities\n$data\n"
            outputFile.outputStream().bufferedWriter().use { output ->
                output.write(content)
            }
        }

        println("Creating backup...")
        val now = LocalDate.now()
        outputFile.copyTo(File(outputFile.parentFile, "output.$now.csv"))

        println("Exporting days data...")
        val newMetadata = MetaData(days = activities.map { it.date })
        json.encodeToString(MetaData.serializer(), newMetadata).let { data ->
            metadataFile.outputStream().bufferedWriter().use { output ->
                output.write(data)
            }
        }
    }
}
