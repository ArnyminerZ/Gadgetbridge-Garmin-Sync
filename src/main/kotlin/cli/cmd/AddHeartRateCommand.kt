package com.arnyminerz.ggs.cli.cmd

import com.arnyminerz.ggs.cli.args.CLIArgument
import com.arnyminerz.ggs.cli.args.Database
import com.arnyminerz.ggs.cli.args.GPX
import com.arnyminerz.ggs.cli.args.Help
import com.arnyminerz.ggs.data.gpx.GpxFile
import com.arnyminerz.ggs.gadgetbridge.Gadgetbridge
import com.arnyminerz.ggs.xml
import java.io.File

object AddHeartRateCommand : Command("add_heart_rate") {
    override val arguments = listOf<CLIArgument>(Help, Database, GPX)

    override fun invoke(arguments: MutableMap<CLIArgument, String?>) {
        if (arguments[Help] != null) {
            println("Usage:")
            println("$name --database <database> --gpx <gpx file>")
            return
        }

        val dbFilePath = arguments[Database]
        requireNotNull(dbFilePath) { "Database file not provided.\nUsage: --database <database>" }

        val gpxFilePath = arguments[GPX]
        requireNotNull(gpxFilePath) { "GPX file not provided.\nUsage: --gpx <gpx file>" }

        val gpxFile = File(gpxFilePath)
        check(gpxFile.exists()) { "GPX file doesn't exist." }
        val gpx = xml.decodeFromString(GpxFile.serializer(), gpxFile.readText())

        val timestamps = gpx.trk.segments
            .flatMap { it.points }
            .map { it.time }
        val gpxStart = timestamps.minOf { it }.toInstant()
        val gpxEnd = timestamps.maxOf { it }.toInstant()
        println("GPX Start time: $gpxStart")
        println("GPX End time: $gpxEnd")

        val dbFile = File(dbFilePath)
        check(dbFile.exists()) { "Database file doesn't exist." }
        Gadgetbridge.connect(dbFile)

        val hrSamples = Gadgetbridge.getHeartRateSamples(gpxStart, gpxEnd)
        println("Here are ${hrSamples.size} HR samples in the time range.")
    }
}
