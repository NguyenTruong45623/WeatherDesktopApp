package model

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.WeatherDay.DataWeather
import model.WeatherFiveDay.WeatherFiveDay
import utils.Constants.API
import utils.NetworkResponse
import utils.printLogs

class NewApiClient {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun defaultApi(): NetworkResponse<DataWeather> {
        try {
            val response = httpClient.get{
                url {
                    takeFrom("https://api.openweathermap.org/data/2.5/weather?q=Danang&appid=$API&units=metric")
                }
            }
            printLogs(response.body())
            return if (response.status == HttpStatusCode.OK) NetworkResponse.Success(response.body()) else NetworkResponse.Failure(
            "Error status code: ${response.status}")

        } catch (e : Exception) {
            return NetworkResponse.Failure("test123 ${e.message!!}")
        }
    }

    suspend fun searchCity(cityName: String): NetworkResponse<DataWeather> {
        try {
            val response = httpClient.get{
                url {
                    takeFrom("https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API&units=metric")
                }
            }
            printLogs(response.body())
            return if (response.status == HttpStatusCode.OK) NetworkResponse.Success(response.body()) else NetworkResponse.Failure(
                "Error status code: ${response.status}")

        } catch (e : Exception) {
            return NetworkResponse.Failure("test123 ${e.message!!}")
        }
    }

    suspend fun getDataFiveDay(cityName: String): NetworkResponse<WeatherFiveDay> {
        try {
            val response = httpClient.get{
                url {
                    takeFrom("https://api.openweathermap.org/data/2.5/forecast?q=$cityName&appid=$API&units=metric")
                }
            }
            printLogs(response.body())
            return if (response.status == HttpStatusCode.OK) NetworkResponse.Success(response.body()) else NetworkResponse.Failure(
                "Error status code: ${response.status}")

        } catch (e : Exception) {
            return NetworkResponse.Failure("test123 ${e.message!!}")
        }
    }
//    suspend fun defaultApi1(): WeatherFiveDay {
//        val url = "https://api.openweathermap.org/data/2.5/forecast?q=hanoi&appid=f75c4ed94a3b3781ebac3b12b6ea2272"
//        return httpClient.get(url).body()
//    }


}
