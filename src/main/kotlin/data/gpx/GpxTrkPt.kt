@file:UseSerializers(TimestampSerializer::class)

package com.arnyminerz.ggs.data.gpx

import com.arnyminerz.ggs.serialization.serialization.TimestampSerializer
import java.time.ZonedDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("trkpt")
data class GpxTrkPt(
    val lat: Double,
    val lon: Double,
    @XmlElement(true) val ele: Double,
    @XmlElement(true) val time: ZonedDateTime,
    val extensions: GpxExtensions? = null
)
