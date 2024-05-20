package Screen.Home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.WeatherDay.DataWeather
import utils.getFormattedDate
import utils.styleIcon
import utils.w5
import utils.w6

@Composable
fun MainConten(headerTitle: String, dataWeather: DataWeather) {
    Column (verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()){

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(headerTitle, style = TextStyle(fontWeight = FontWeight.W700,fontSize = 35.sp))
            Spacer(modifier = Modifier.height(10.dp))
            for (index in dataWeather.weather.indices) {
                Text("Weather forecast ToDay ${dataWeather.weather[index].main}", style = TextStyle(fontWeight = FontWeight.W700,fontSize = 30.sp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(getFormattedDate(dataWeather.dt))
        }

        Row(horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                ) {
            for (index in dataWeather.weather.indices) {
                Image(
                    painterResource("${dataWeather.weather[index].icon}.png"),
                    "news thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(120.dp)
                        .padding(8.dp) // Add padding for aesthetics
                        .scale(scale = 1.5f)
                )

            }
            Spacer(modifier = Modifier.width(40.dp))
            Column {
                Text("${dataWeather.main.temp}°C",
                    style = TextStyle(fontWeight = FontWeight.W500,fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text("${dataWeather.main.tempMin}°C/${dataWeather.main.tempMax}°C",
                    style = TextStyle(fontWeight = FontWeight.W500,fontSize = 18.sp)
                )
            }
            Spacer(modifier = Modifier.width(40.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painterResource("windspeed.png"), contentDescription = "person",modifier = Modifier.size(styleIcon))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("${dataWeather.wind.speed}m/s",
                        style = TextStyle(fontWeight = FontWeight.W500,fontSize = 18.sp)
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painterResource("cloudy.png"), contentDescription = "person",modifier = Modifier.size(styleIcon))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("${dataWeather.clouds.all}%",
                        style = TextStyle(fontWeight = FontWeight.W500,fontSize = 18.sp)
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painterResource("humidity.png"), contentDescription = "person",modifier = Modifier.size(styleIcon))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("${dataWeather.main.humidity}%",
                        style = TextStyle(fontWeight = FontWeight.W500,fontSize = 18.sp)
                    )
                }
            }

        }

    }

}

