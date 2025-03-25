package me.alekseinovikov.todo

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "todo-kotlin-compose",
        state = rememberWindowState(size = DpSize(1200.dp, 800.dp))
    ) {
        App()
    }
}