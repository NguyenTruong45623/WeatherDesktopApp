package model.WeatherFiveDay


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    @SerialName("pod")
    val pod: String
)