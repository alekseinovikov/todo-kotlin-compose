package me.alekseinovikov.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.alekseinovikov.todo.data.Todo
import me.alekseinovikov.todo.data.TodoViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@ExperimentalMaterialApi
@Preview
@Composable
fun App(todoModel: TodoViewModel) {
    var selectedItem by remember { mutableStateOf<Todo?>(null) }
    var currentScreen by remember { mutableStateOf("main") }
    val items = todoModel.items.value
    val isLoading = todoModel.isLoading.value

    when (currentScreen) {
        "main" -> {
            LaunchedEffect(Unit) {
                todoModel.refreshData()
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        items(items) { item ->
                            val color = if (item.id == selectedItem?.id)
                                MaterialTheme.colors.primary
                            else MaterialTheme.colors.surface

                            key(item.toString()) {
                                ListItem(
                                    modifier = Modifier
                                        .background(color)
                                        .fillMaxWidth()
                                        .clickable { selectedItem = item }
                                        .padding(8.dp),
                                    text = { Text(text = item.title) }
                                )
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
                            Text(text = "Содержимое: ${selectedItem?.title}")
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
                FloatingActionButton(
                    onClick = { currentScreen = "add" },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить новый Todo")
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

        "add" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                var newTodoTitle by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = newTodoTitle,
                        onValueChange = { newTodoTitle = it },
                        label = { Text("Новый Todo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(onClick = {
                            if (newTodoTitle.isNotBlank()) {
                                todoModel.addNewTodo(newTodoTitle)
                                newTodoTitle = ""
                                currentScreen = "main"
                            }
                        }) {
                            Text("Добавить")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = { currentScreen = "main" }) {
                            Text("Отмена")
                        }
                    }
                }
            }
        }
    }
}