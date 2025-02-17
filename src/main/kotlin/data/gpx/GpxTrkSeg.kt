package com.arnyminerz.ggs.data.gpx

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("trkseg")
data class GpxTrkSeg(
    val points: List<GpxTrkPt>
)
