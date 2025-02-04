package com.arnyminerz.ggs.com.arnyminerz.ggs.gadgetbridge.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object ColmiActivitySampleTable : IdTable<Int>("COLMI_ACTIVITY_SAMPLE") {
    override val id: Column<EntityID<Int>> = integer("TIMESTAMP").entityId()

    val timestamp get() = id
    val deviceId = integer("DEVICE_ID")
    val userId = integer("USER_ID")
    val rawKind = integer("RAW_KIND")
    val steps = integer("STEPS")
    val heartRate = integer("HEART_RATE")
    val distance = integer("DISTANCE")
    val calories = integer("CALORIES")
}
