package me.alekseinovikov.todo.data

import jakarta.persistence.*

@Entity
@Table(name = "todos")
data class Todo(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @field:Column(nullable = false)
    val title: String,

    @field:Column(nullable = false)
    val completed: Boolean = false
) {
    constructor() : this(0, "", false)
}
