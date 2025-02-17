package com.arnyminerz.ggs.gadgetbridge.tables

import java.time.Instant
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ColmiHeartRateSample(id: EntityID<Int>): Entity<Int>(id) {
    companion object : EntityClass<Int, ColmiHeartRateSample>(ColmiHeartRateSampleTable)

    val timestamp: Instant get() = Instant.ofEpochSecond(id.value.toLong())
    val deviceId by ColmiHeartRateSampleTable.deviceId
    val userId by ColmiHeartRateSampleTable.userId
    val heartRate by ColmiHeartRateSampleTable.heartRate
}
