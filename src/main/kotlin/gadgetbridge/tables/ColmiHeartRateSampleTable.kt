package com.arnyminerz.ggs.gadgetbridge.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object ColmiHeartRateSampleTable : IdTable<Int>("COLMI_HEART_RATE_SAMPLE") {
    override val id: Column<EntityID<Int>> = integer("TIMESTAMP").entityId()

    val deviceId = integer("DEVICE_ID")
    val userId = integer("USER_ID")
    val heartRate = integer("HEART_RATE")
}
