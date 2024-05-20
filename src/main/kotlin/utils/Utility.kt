package utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//fun loadPicture(url: String) =
//    Image.makeFromEncoded(URL(url).readBytes())
//        .toComposeImageBitmap()


fun getFormattedDate(dt : Int): String {
    val instant = Instant.ofEpochSecond(dt.toLong())
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

fun downTheLine(inputString : String): String {
//    val inputString = "2024-04-03 00:00:00"

    // Chia chuỗi thành các dòng bằng cách sử dụng regex \n\n hoặc \r\n\r\n
    val lines = inputString.split(" ".toRegex())

    // Nối các dòng lại với nhau và xuống dòng sau mỗi dòng
    val result = lines.joinToString("\n")

    return result
}

fun printLogs(log: Any){
    println("CodeRunner Logs: $log")
}