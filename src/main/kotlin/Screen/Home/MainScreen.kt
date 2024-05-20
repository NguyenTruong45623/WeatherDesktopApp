package Screen.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import model.NewApiClient
import Screen.SearchHistoryScreen
import Screen.WeatherFiveDay.FiveDayWeatherForecast
import utils.dialogs
import viewModel.MainScreenModel
import viewModel.WeatherFiveDayScreenModel


class MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var searchedText by remember { mutableStateOf("") }
        var CityName by remember { mutableStateOf("") }

        val viewModelWeather = navigator?.rememberNavigatorScreenModel {
            MainScreenModel(
                NewApiClient()
            )
        }
        val viewModelFiveDay = navigator?.rememberNavigatorScreenModel { WeatherFiveDayScreenModel(NewApiClient()) }

        val stateWeather = viewModelWeather?.stateWeather?.collectAsState()
        val dataWeather = viewModelWeather?.dataWeather?.collectAsState()
        val searchCity = viewModelWeather?.searchDataCity?.collectAsState()

        val isButtonPressed = remember { mutableStateOf(false) }

        MaterialTheme {

            Column (horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()){
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround)
                {
                    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.weight(4F)) {
                        Image(painterResource("person.png"), contentDescription = "person")
                        Column {
                            Text("Hello,")
                            Text("What's the Weather")
                        }
                    }

                    Column(modifier = Modifier.weight(3F),
                        horizontalAlignment = Alignment.End,)
                    {
                        Row(verticalAlignment = Alignment.CenterVertically,

                            modifier = Modifier.padding(horizontal = 10.dp)
                                .fillMaxWidth()
                        ) {
                            TextField(
                                value = searchedText.take(50),
                                onValueChange = { searchedText = it },
                                label = { Text("Enter City Name") },
                                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold,),
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.LightGray, // Đặt màu nền trong suốt
                                    cursorColor = Color.Black, // Đặt màu của con trỏ
                                    focusedIndicatorColor = Color.Transparent, // Loại bỏ đường viền khi được focus
                                    unfocusedIndicatorColor = Color.Transparent // Loại bỏ đường viền khi không được focus
                                ),
                                maxLines = 1,
                                modifier = Modifier.weight(4F)
                            )
                            Button(
                                onClick = {
                                    viewModelWeather?.searchCity(searchedText)
                                    CityName = if (searchedText.isNotEmpty()){
                                        searchedText
                                    } else "Đà Nẵng"
                                    println("city name : ___$CityName")
                                    println("city name : ___$searchedText")
                                    searchedText = ""
                                },
                                modifier = Modifier.padding(start = 10.dp)
                                    .weight(1F)
                                    .fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Search, "", tint = Color.White)
                            }
                        }

                        TextButton(
                            onClick = { navigator?.push(SearchHistoryScreen()) }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Search History")
                                Spacer(modifier = Modifier.width(5.dp))
                                Image(painterResource("searchHistory.png"), contentDescription = "icon searchHistory",
                                    modifier = Modifier.size(20.dp))
                            }
                        }
                    }

                }
                Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(color = Color.Blue.copy(alpha = 0.2f),)) {
                    when(stateWeather?.value) {
                        is MainUiState.Error -> {
                            Text(
                                "Some error occurred: ${(stateWeather.value as MainUiState.Error).message}",
                                style = TextStyle(color = Color.Black, fontSize = 24.sp)
                            )

                            dialogs(viewModelWeather)
                        }
                        MainUiState.Loading -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                        is MainUiState.Success -> {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                dataWeather?.value?.let {
                                    searchCity?.let { it1 -> MainConten(it1.value, it) }
                                }
                            }
                            TextButton(
                                onClick = {
                            searchCity?.let { viewModelFiveDay?.getCity(it.value) }

//                                    viewModelFiveDay?.getCity(CityName)
                                    navigator.push(FiveDayWeatherForecast())
                                },
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("dự báo 5 ngày tiếp theo", style = TextStyle(fontWeight = FontWeight.W600, fontSize = 18.sp))
                                    Icon(Icons.Default.ArrowForward, "", tint = Color.White)
                                }
                            }
                        }
                        null -> TODO()
                    }

                }
            }

        }
    }
}