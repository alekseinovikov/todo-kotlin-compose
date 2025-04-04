package me.alekseinovikov.todo

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import me.alekseinovikov.todo.data.TodoViewModel
import me.alekseinovikov.todo.properties.ApplicationProperties
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class TodoApplication(
    private val applicationProperties: ApplicationProperties,
    private val todoModel: TodoViewModel
) : CommandLineRunner {
    @OptIn(ExperimentalMaterialApi::class)
    override fun run(vararg args: String?) = application {
        Window(
            onCloseRequest = ::exitApplication,
            title = applicationProperties.title,
            state = rememberWindowState(size = DpSize(1200.dp, 800.dp))
        ) {
            App(todoModel)
        }
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(TodoApplication::class.java)
        .web(WebApplicationType.NONE)
        .headless(false)
        .run()
}
