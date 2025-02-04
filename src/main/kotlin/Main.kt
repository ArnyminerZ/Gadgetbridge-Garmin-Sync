package com.arnyminerz.ggs

import cli.ArgumentParser
import cli.Database
import cli.Metadata
import data.Activity
import data.MetaData
import gadgetbridge.Gadgetbridge
import java.io.File
import java.time.LocalDate
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@ExperimentalSerializationApi
val csv = Csv { hasHeaderRecord = true }

val json = Json { prettyPrint = true }

@OptIn(ExperimentalSerializationApi::class)
fun main(args: Array<String>) {
    val arguments = ArgumentParser.parse(args)

    val dbFilePath = arguments[Database]
    requireNotNull(dbFilePath) { "Database file not provided.\nUsage: --database <database>" }

    val metadataFilePath = arguments[Metadata] ?: "metadata.json"
    val metadataFile = File(metadataFilePath)

    val outputFilePath = arguments[Metadata] ?: "output.csv"
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
