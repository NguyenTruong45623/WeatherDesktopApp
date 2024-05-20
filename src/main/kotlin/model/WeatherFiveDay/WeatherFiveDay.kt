package model.WeatherFiveDay


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherFiveDay(
    @SerialName("city")
    val city: City,
    @SerialName("cnt")
    val cnt: Int,
    @SerialName("cod")
    val cod: String,
    @SerialName("list")
    val listdata: List<DataWeather>,
    @SerialName("message")
    val message: Int
)