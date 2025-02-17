@file:UseSerializers(TimestampSerializer::class)

package com.arnyminerz.ggs.data.gpx

import com.arnyminerz.ggs.serialization.serialization.TimestampSerializer
import java.time.ZonedDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("metadata")
data class GpxMetadata(
    @XmlElement(true) val name: String,
    val author: GpxAuthor,
    val link: GpxLink,
    @XmlElement(true) val time: ZonedDateTime,
)
