package me.alekseinovikov.todo.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "application.properties")
data class ApplicationProperties @ConstructorBinding constructor(val title: String)