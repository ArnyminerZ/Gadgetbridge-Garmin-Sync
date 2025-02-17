package com.arnyminerz.ggs.data.gpx

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName(namespace = NS_GARMIN, value = "TrackPointExtension")
data class GpxTrackPointExtension(
    @XmlElement(true) @XmlSerialName(namespace = NS_GARMIN, value = "atemp") val temperature: Double,
    @XmlElement(true) @XmlSerialName(namespace = NS_GARMIN, value = "hr") val heartRate: Int,
    @XmlElement(true) @XmlSerialName(namespace = NS_GARMIN, value = "cad") val cadence: Int,
)
