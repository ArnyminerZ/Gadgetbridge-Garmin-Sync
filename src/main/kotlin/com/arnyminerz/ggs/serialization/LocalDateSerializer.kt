package com.arnyminerz.ggs.com.arnyminerz.ggs.serialization

import java.time.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LocalDateSerializer: KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("com.arnyminerz.ggs.serialization.LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val year = value.year
        val month = value.monthValue.toString().padStart(2, '0')
        val day = value.dayOfMonth.toString().padStart(2, '0')
        encoder.encodeString("$year-$month-$day")
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val str = decoder.decodeString()
        val (year, month, day) = str.split("-").map { it.toInt() }
        return LocalDate.of(year, month, day)
    }
}
