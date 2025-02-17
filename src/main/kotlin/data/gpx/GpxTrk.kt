package com.arnyminerz.ggs.data.gpx

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("trk")
data class GpxTrk(
    @XmlElement(true) val name: String,
    @XmlElement(true) val cmt: String,
    @XmlElement(true) val desc: String,
    val segments: List<GpxTrkSeg>,
)
