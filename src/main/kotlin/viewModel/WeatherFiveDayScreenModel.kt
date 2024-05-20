package viewModel

import Screen.WeatherFiveDay.FiveDayWeatherState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.NewApiClient
import model.WeatherFiveDay.WeatherFiveDay
import utils.NetworkResponse

class WeatherFiveDayScreenModel(private val newApiClient: NewApiClient): ScreenModel {
    private var _stateFiveWeather = MutableStateFlow<FiveDayWeatherState>(FiveDayWeatherState.Loading)
    val stateFiveWeather: StateFlow<FiveDayWeatherState> = _stateFiveWeather
    private var _getFiveWeathers = MutableStateFlow<WeatherFiveDay?>(null)
    val getFiveWeathers: MutableStateFlow<WeatherFiveDay?> = _getFiveWeathers
    private var _getCityNameQuery = MutableStateFlow<String>("")
    val getCityNameQuery: MutableStateFlow<String> = _getCityNameQuery

    init {
        getDataApiWeatherFiveDay()
    }
    private fun getDataApiWeatherFiveDay() {
        screenModelScope.launch {
            var result = newApiClient.getDataFiveDay(_getCityNameQuery.value)

            when(result) {
                is NetworkResponse.Failure -> {
                    _stateFiveWeather.value = FiveDayWeatherState.Error(result.message)
                }
                is NetworkResponse.Loading -> {
                    _stateFiveWeather.value = FiveDayWeatherState.Loading
                    println("texxxxt")
                }
                is NetworkResponse.Success -> {
                    _getFiveWeathers.value = result.value
                    _stateFiveWeather.value = FiveDayWeatherState.Success(result.value)
                }
            }
        }
    }
    fun getCity(cityName: String) {
        _getCityNameQuery.value = cityName
        getDataApiWeatherFiveDay()
        println("test api nameCity: ${ _getCityNameQuery.value }")
    }


}