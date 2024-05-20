package Screen.WeatherFiveDay

import model.WeatherFiveDay.WeatherFiveDay

sealed class FiveDayWeatherState {
    object Loading: FiveDayWeatherState()
    data class Success(val data: WeatherFiveDay): FiveDayWeatherState()
    data class Error(val message: String): FiveDayWeatherState()
}