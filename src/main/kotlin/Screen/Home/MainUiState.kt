package Screen.Home

import model.WeatherDay.DataWeather


sealed class MainUiState {
    object Loading: MainUiState()
    data class Success(val data: DataWeather): MainUiState()
    data class Error(val message: String): MainUiState()
}