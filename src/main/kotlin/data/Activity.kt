package data

import java.time.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import serialization.LocalDateSerializer

@Serializable
data class Activity(
    @SerialName("Date")
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,
    @SerialName("Calories Burned")
    val caloriesBurned: Int,
    @SerialName("Steps")
    val steps: Int,
    @SerialName("Distance")
    val distance: Float,
    @SerialName("Floors")
    val floors: Int = 0,
    @SerialName("Minutes Sedentary")
    val minutesSedentary: Int = 0,
    @SerialName("Minutes Lightly Active")
    val minutesLightlyActive: Int = 0,
    @SerialName("Minutes Fairly Active")
    val minutesFairlyActive: Int = 0,
    @SerialName("Minutes Very Active")
    val minutesVeryActive: Int = 0,
    @SerialName("Activity Calories")
    val activityCalories: Int = caloriesBurned,
)
