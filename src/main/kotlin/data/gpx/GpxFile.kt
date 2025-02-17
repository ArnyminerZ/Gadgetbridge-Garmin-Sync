@file:Suppress("HttpUrlsUsage")

package com.arnyminerz.ggs.data.gpx

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

const val NS_GARMIN = "http://www.garmin.com/xmlschemas/TrackPointExtension/v1"
const val NS_XSI = "http://www.w3.org/2001/XMLSchema-instance"

@Serializable
data class GpxFile(
    val creator: String,
    val version: String,
    @XmlSerialName(namespace = NS_XSI) val schemaLocation: String,
    val metadata: GpxMetadata,
    val trk: GpxTrk,
)
