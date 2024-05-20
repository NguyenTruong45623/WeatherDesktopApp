package Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.db.SearchHistory
import kotlinx.coroutines.launch
import presentation.vm.CustomViewModel
import presentation.vm.CustomViewModelGet

class SearchHistoryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val myVMGet = CustomViewModelGet()
        val myVM = CustomViewModel()

        var result by remember { mutableStateOf(listOf<SearchHistory>()) }
        var isLoading by remember { mutableStateOf(false) }
        var isDeleting by remember { mutableStateOf(false) } // Thêm biến trạng thái cho việc xóa

        val coroutineScope = rememberCoroutineScope()

        myVMGet.getAll() {
            coroutineScope.launch {
                myVMGet.myBpStateFlow.collect { it ->
                    isLoading = true
                    if (it != null) {
                        result = it
                    }
                    isLoading = false
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Lịch sử Tìm Kiếm",modifier = Modifier.fillMaxWidth(), // Đặt chiều rộng của Text là tối đa
                                textAlign = TextAlign.Center ) },
                            navigationIcon = {
                                IconButton(onClick = { navigator?.pop() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back Arrow icon"
                                    )
                                }
                            },

                            actions = {
                                // Thêm các nút hoạt động vào đây
                                Button(onClick = { coroutineScope.launch {
                                    isDeleting = true // Đặt trạng thái xóa là true
                                    myVM.deleteAll()
                                    isDeleting = false // Đặt trạng thái xóa là false sau khi hoàn thành
                                }}) {
                                    Row {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete All")
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text("Delete All") // Chữ kèm theo nút hoạt động
                                    }
                                }
                            },
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .fillMaxHeight()
                            .background(color = Color.White)
                    ) {


                    }
                    LazyColumn(Modifier.fillMaxSize().padding(end = 12.dp)) {
                        items(result) {
                            it.temp?.let { it1 ->
                                it.event_time?.let { it2 ->
                                    TextBox(
                                        it.id.toInt(), it.city, it1,
                                        it2, myVM
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextBox(id: Int, city: String, temp: Double, event_time: String, myVM: CustomViewModel?) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(32.dp)
            .fillMaxWidth()
            .background(color = Color(0x8D6E63))
            .padding(start = 10.dp),
    ) {
        Text(text = id.toString())
        Text(text = city)
        Text(text = temp.toString())
        Text(text = event_time)
        IconButton(onClick = {
            myVM?.deleteSearchHistory(id.toInt())
        }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

