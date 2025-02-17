package com.arnyminerz.ggs.data.gpx

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("author")
data class GpxAuthor(
    @XmlElement(true) val name: String,
    val link: GpxLink,
)
