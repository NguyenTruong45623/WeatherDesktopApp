package viewModel

import Screen.Home.MainUiState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.WeatherDay.DataWeather
import model.NewApiClient
import presentation.vm.CustomViewModel
import utils.NetworkResponse
import utils.getFormattedDate
import utils.printLogs

class MainScreenModel(private val newApiClient: NewApiClient): ScreenModel {
    private var _stateWeather = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val stateWeather: StateFlow<MainUiState> = _stateWeather
    private var _dataWeather = MutableStateFlow<DataWeather?>(null)
    val dataWeather: MutableStateFlow<DataWeather?> = _dataWeather
    private var _searchDataCity = MutableStateFlow<String>("Đà Nẵng")
    val searchDataCity: MutableStateFlow<String> = _searchDataCity
    val myVM = CustomViewModel()

    init {
        getDataAPIWeather()
        printLogs("Local language is empty")
    }

    private fun getDataAPIWeather() {
        printLogs("Local language is empty1")
        screenModelScope.launch {
//            println("city3: ${_searchDataCity.value}")
            val result = newApiClient.searchCity(_searchDataCity.value)

//            println("city5: ${_searchDataCity.value}")
            when(result) {
                is NetworkResponse.Loading -> {
                    _stateWeather.value = MainUiState.Loading
                }
                is NetworkResponse.Success -> {
                    _dataWeather.value = result.value
                    myVM.insertSearchHistory(_searchDataCity.value, _dataWeather.value!!.main.temp,getFormattedDate(dataWeather.value!!.dt))
                    _stateWeather.value = MainUiState.Success(result.value)
                }
                is NetworkResponse.Failure -> {
                    _stateWeather.value = MainUiState.Error(result.message)
                }
            }
        }

    }

    fun searchCity(searchTerm: String) {
        _searchDataCity.value = searchTerm
        getDataAPIWeather()
    }
}