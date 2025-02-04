package com.arnyminerz.ggs.com.arnyminerz.ggs.data

import com.arnyminerz.ggs.com.arnyminerz.ggs.serialization.LocalDateSerializer
import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class MetaData(
    val days: List<@Serializable(with = LocalDateSerializer::class) LocalDate> = emptyList()
)
