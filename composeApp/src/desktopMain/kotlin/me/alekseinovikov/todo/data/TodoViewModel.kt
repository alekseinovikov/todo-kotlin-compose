package me.alekseinovikov.todo.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class TodoViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    val items = mutableStateOf<List<Todo>>(emptyList())
    val isLoading = mutableStateOf(false)

    fun addNewTodo(title: String) {
        runWithReloadingData {
            todoRepository.save(
                Todo(
                    title = title
                )
            )
        }
    }

    fun deleteItem(todo: Todo) {
        runWithReloadingData { todoRepository.delete(todo) }
    }

    fun refreshData() {
        runWithReloadingData {}
    }

    private fun runWithReloadingData(block: suspend () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            items.value = withContext(Dispatchers.IO) {
                block()
                todoRepository.findAll()
            }
            isLoading.value = false
        }
    }

}