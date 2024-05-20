package utils

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import viewModel.MainScreenModel

@Composable
fun dialogs(mainScreenModel: MainScreenModel) {
    val showDialog = remember { mutableStateOf(true) }

    // Function to show the dialog
    fun showAlertDialog() {
        showDialog.value = true
    }

    // Function to hide the dialog
    fun hideAlertDialog() {
        showDialog.value = false
    }


    // Dialog component
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { hideAlertDialog() },
            title = {
                Text(text = "Dialog Title")
            },
            text = {
                Text(text = "Không có dữ liệu thời tiết về thành phố này!!!")
            },
            confirmButton = {
                Button(onClick = {
                    hideAlertDialog()
                    mainScreenModel.searchCity("Đà Nẵng")
                }) {
                    Text(text = "OK")
                }
            }
        )
    }
}