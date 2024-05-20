
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import Screen.SplashScreen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray

@Composable
fun App()  {
    MaterialTheme {
        Navigator(SplashScreen()){
            SlideTransition(it)
        }
    }
}
fun main() = application {
    val icon = painterResource("icon_app2.png")

    Tray(
        icon = icon,
        menu = {
            Item("Quit App", onClick = ::exitApplication)
        }
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "Weather",
        resizable = false,
        icon = icon
    ) {
        App()
    }
}
