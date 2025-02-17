package com.arnyminerz.ggs.gadgetbridge

import com.arnyminerz.ggs.data.Activity
import com.arnyminerz.ggs.data.HeartRateSample
import com.arnyminerz.ggs.gadgetbridge.tables.ColmiActivitySample
import com.arnyminerz.ggs.gadgetbridge.tables.ColmiHeartRateSample
import com.arnyminerz.ggs.gadgetbridge.tables.ColmiHeartRateSampleTable
import java.io.File
import java.sql.Connection
import java.time.Instant
import java.time.LocalDate
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

object Gadgetbridge {
    private lateinit var database: Database

    fun connect(file: File) {
        database = Database.Companion.connect(
            "jdbc:sqlite:${file.absolutePath}",
            "org.sqlite.JDBC",
            databaseConfig = DatabaseConfig.Companion {
                defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
            },
        )
    }

    fun getActivities(excludeDays: List<LocalDate>): List<Activity> = transaction(database) {
        ColmiActivitySample.Companion.all()
            .groupBy { it.dateTime.toLocalDate() }
            .toSortedMap()
            // Filter already exported data
            .filter { (date) -> !excludeDays.contains(date) }
            .map { (date, sample) ->
                Activity(
                    date = date,
                    steps = sample.sumOf { it.steps },
                    distance = sample.sumOf { it.distance }.toFloat() / 1000, // distance output is in kms
                    caloriesBurned = sample.sumOf { it.calories },
                )
            }
            // Remove the last day, since it will be most likely not complete (aka before 23:59:59)
            // This is because Garmin only imports data for full days
            .dropLast(1)
    }

    fun getHeartRateSamples(start: Instant, end: Instant): List<HeartRateSample> = transaction(database) {
        val startEpoch = start.toEpochMilli().div(1000).toInt()
        val endEpoch = end.toEpochMilli().div(1000).toInt()

        println("Range: $startEpoch - $endEpoch")

        ColmiHeartRateSample.find {
            (ColmiHeartRateSampleTable.id greaterEq startEpoch) and (ColmiHeartRateSampleTable.id lessEq endEpoch) and
                    (ColmiHeartRateSampleTable.heartRate greater 0)
        }
            .associate { it.timestamp to it.heartRate }
            .map { HeartRateSample(it.key, it.value) }
    }
}
