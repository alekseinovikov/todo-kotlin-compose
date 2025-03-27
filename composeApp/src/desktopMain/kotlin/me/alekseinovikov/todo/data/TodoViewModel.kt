package me.alekseinovikov.todo.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class TodoViewModel : ViewModel() {

    val items = mutableStateOf<List<String>>(emptyList())
    val isLoading = mutableStateOf(false)

    private var counter = AtomicInteger(0)

    init {
        refreshData()
    }

    fun deleteItem(item: String) {
        viewModelScope.launch {
            isLoading.value = true
            items.value = withContext(Dispatchers.IO) {
                // Здесь запрос к сети или базе данных
                delay(1000) // имитация задержки
                items.value.filter { it != item }
            }
            isLoading.value = false
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            isLoading.value = true
            // Выполнение загрузки данных в фоне
            val data = withContext(Dispatchers.IO) {
                // Здесь запрос к сети или базе данных
                delay(1000) // имитация задержки
                val data = mutableListOf<String>()
                for (i in 0 until 5) {
                    val number = counter.incrementAndGet()
                    data.add("Item $number")
                }

                data
            }
            // Обновляем состояние в главном потоке
            items.value = data
            isLoading.value = false
        }
    }

}