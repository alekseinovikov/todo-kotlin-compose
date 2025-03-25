package me.alekseinovikov.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@ExperimentalMaterialApi
@Preview
@Composable
fun App() {
    var items by remember {
        mutableStateOf<MutableList<String>>(
            mutableListOf(
                "Элемент 1",
                "Элемент 2",
                "Элемент 3",
                "Элемент 4"
            )
        )
    }
    var selectedItem by remember { mutableStateOf<String?>(null) }

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
                    items = items.filter { it != selectedItem }.toMutableList()
                    selectedItem = null
                }) {
                    Text(text = "Удалить")
                }
            } else {
                Text(text = "Выберите элемент из списка")
            }
        }
    }
}
