package com.agile.engine.challenge.infrastructure.model

data class TasksConfig(
    val reloadCache: ReloadCacheConfig
)

data class ReloadCacheConfig(
    val intervalInSeconds: Long
)