package com.arnyminerz.ggs.data.gpx

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("extensions")
data class GpxExtensions(
    val trackPointExtension: GpxTrackPointExtension? = null
)
