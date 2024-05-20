package Screen.WeatherFiveDay

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.ktor.client.plugins.*
import kotlinx.coroutines.launch
import model.NewApiClient
import model.WeatherFiveDay.WeatherFiveDay
import utils.downTheLine
import viewModel.WeatherFiveDayScreenModel

class FiveDayWeatherForecast() : Screen, ScreenModel {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModelFiveDay = navigator?.rememberNavigatorScreenModel { WeatherFiveDayScreenModel(NewApiClient()) }
        val dataFiveWeather = viewModelFiveDay?.getFiveWeathers?.collectAsState()
        val stateFiveWeather = viewModelFiveDay?.stateFiveWeather?.collectAsState()
        val getCityNameQuery = viewModelFiveDay?.getCityNameQuery?.collectAsState()

        val dataWeather = dataFiveWeather?.value?.listdata
        MaterialTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Dự báo thời tiết trong 5 ngày của ${getCityNameQuery?.value}",
                            modifier = Modifier.fillMaxWidth(), // Đặt chiều rộng của Text là tối đa
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontWeight = FontWeight.W600, fontSize = 18.sp))
                        },
                        navigationIcon = {
                            IconButton(onClick = { navigator?.pop() }){
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back Arrow icon"
                                )
                            }
                        }
                    )
                }
            ) {
                Column(modifier = Modifier.background(color = Color.Blue.copy(alpha = 0.2f),)) {

                    when(stateFiveWeather?.value) {
                        is FiveDayWeatherState.Error -> {

                        }
                        is FiveDayWeatherState.Loading -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                        is FiveDayWeatherState.Success -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(5),

                                // content padding
                                contentPadding = PaddingValues(
                                    start = 12.dp,
                                    top = 16.dp,
                                    end = 12.dp,
                                    bottom = 16.dp
                                ),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                content = {
                                    if (dataWeather != null) {
                                        items(dataWeather.size) { index ->
                                            Card(
                                                backgroundColor = Color(0xff81D4FA),
                                                modifier = Modifier
                                                    .padding(6.dp)
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(10.dp)),
                                                elevation = 8.dp,
                                            ) {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center,
                                                    modifier = Modifier.padding(bottom = 10.dp)
                                                ) {
                                                    Spacer(modifier = Modifier.height(5.dp))
                                                    Text(
                                                        text = downTheLine(dataWeather[index].dtTxt),
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(vertical = 3.dp)
                                                        ,
                                                        style = TextStyle(fontWeight = FontWeight.W600, fontSize = 15.sp)
                                                    )
                                                    for (icons in dataWeather[index].weather.indices) {
                                                        Image(
                                                            painterResource("${dataWeather[index].weather[icons].icon}.png"),
                                                            "news thumbnail",
                                                            modifier = Modifier.size(80.dp)
                                                        )
                                                        Text(
                                                            text = "${dataWeather[index].main.temp}°C",
                                                            style = TextStyle(fontWeight = FontWeight.W800, fontSize = 18.sp, color = Color(0xffE53935))
                                                        )
                                                        Spacer(modifier = Modifier.height(5.dp))
                                                        Text(
                                                            text = dataWeather[index].weather[icons].description,
                                                            style = TextStyle(fontWeight = FontWeight.W600)
                                                        )
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            )
                        }
                        null -> TODO()
                    }


                }
            }
        }

    }
}