package com.agile.engine.challenge.infrastructure.model

data class TasksConfig(
    val reloadCache: ReloadCache
)

data class ReloadCache(
    val intervalInSeconds: Long
)