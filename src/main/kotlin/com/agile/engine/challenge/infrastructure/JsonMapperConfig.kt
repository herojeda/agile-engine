package com.agile.engine.challenge.infrastructure

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.vertx.core.json.jackson.DatabindCodec

class JsonMapperConfig {

    companion object {
        fun applyConfig() {
            DatabindCodec.mapper().apply {
                this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                this.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                this.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
                this.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                this.registerModule(JavaTimeModule())
                this.registerModule(KotlinModule())
                this.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            }
        }
    }
}