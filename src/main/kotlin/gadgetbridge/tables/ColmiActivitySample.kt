package com.arnyminerz.ggs.gadgetbridge.tables

import java.time.LocalDateTime
import java.time.ZoneOffset
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ColmiActivitySample(id: EntityID<Int>): Entity<Int>(id) {
    companion object : EntityClass<Int, ColmiActivitySample>(ColmiActivitySampleTable)

    val timestamp by ColmiActivitySampleTable.timestamp
    val deviceId by ColmiActivitySampleTable.deviceId
    val userId by ColmiActivitySampleTable.userId
    val rawKind by ColmiActivitySampleTable.rawKind
    val steps by ColmiActivitySampleTable.steps
    val heartRate by ColmiActivitySampleTable.heartRate
    val distance by ColmiActivitySampleTable.distance
    val calories by ColmiActivitySampleTable.calories

    val dateTime: LocalDateTime get() = LocalDateTime.ofEpochSecond(timestamp.value.toLong(), 0, ZoneOffset.UTC)
}
