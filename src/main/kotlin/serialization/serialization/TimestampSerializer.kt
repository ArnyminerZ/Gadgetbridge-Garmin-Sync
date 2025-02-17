package com.arnyminerz.ggs.serialization.serialization

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object TimestampSerializer : KSerializer<ZonedDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("com.arnyminerz.ggs.serialization.ZonedDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeString(
            DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value)
        )
    }

    override fun deserialize(decoder: Decoder): ZonedDateTime {
        val str = decoder.decodeString()
        return ZonedDateTime.parse(str, DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }
}
