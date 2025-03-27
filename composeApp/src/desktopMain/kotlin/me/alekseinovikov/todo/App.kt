package me.alekseinovikov.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.alekseinovikov.todo.data.TodoViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@ExperimentalMaterialApi
@Preview
@Composable
fun App(todoModel: TodoViewModel) {
    var selectedItem by remember { mutableStateOf<String?>(null) }
    val items = todoModel.items.value
    val isLoading = todoModel.isLoading.value

    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                items(items) { item ->

                    val color = if (item == selectedItem) MaterialTheme.colors.primary else MaterialTheme.colors.surface
                    key(item.toString()) {
                        ListItem(
                            modifier = Modifier
                                .background(color)
                                .fillMaxWidth()
                                .clickable { selectedItem = item }
                                .padding(8.dp),
                            text = { Text(text = item) }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { todoModel.refreshData() }) {
                        Text(text = "Обновить данные")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                if (selectedItem != null) {
                    Text(text = "Содержимое: $selectedItem")
                    Button(onClick = {
                        todoModel.deleteItem(selectedItem!!)
                        selectedItem = null
                    }) {
                        Text(text = "Удалить")
                    }
                } else {
                    Text(text = "Выберите элемент из списка")
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
