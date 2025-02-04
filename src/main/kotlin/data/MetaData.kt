package data

import java.time.LocalDate
import kotlinx.serialization.Serializable
import serialization.LocalDateSerializer

@Serializable
data class MetaData(
    val days: List<@Serializable(with = LocalDateSerializer::class) LocalDate> = emptyList()
)
