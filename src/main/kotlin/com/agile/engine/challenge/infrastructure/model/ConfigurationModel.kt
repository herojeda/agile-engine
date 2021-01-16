package com.agile.engine.challenge.infrastructure.model


data class ConfigurationModel(
    val system: SystemConfig,
    val tasks: TasksConfig,
    val client: ClientConfig
)