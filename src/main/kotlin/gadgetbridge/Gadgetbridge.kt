package com.arnyminerz.ggs.gadgetbridge

import com.arnyminerz.ggs.data.Activity
import com.arnyminerz.ggs.gadgetbridge.tables.ColmiActivitySample
import java.io.File
import java.sql.Connection
import java.time.LocalDate
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
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
}