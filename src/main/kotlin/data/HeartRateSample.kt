package com.arnyminerz.ggs.data

import java.time.Instant

data class HeartRateSample(
    val timestamp: Instant,
    val value: Int,
)
