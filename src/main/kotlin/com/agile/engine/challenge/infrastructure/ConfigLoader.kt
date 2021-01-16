package com.agile.engine.challenge.infrastructure

import io.reactivex.Single
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.config.ConfigRetriever
import io.vertx.reactivex.core.Vertx


class ConfigLoader(private val vertx: Vertx, base: String = "configuration") {

    private val basePath = base

    fun read(): Single<JsonObject> {

        val baseConfig = ConfigStoreOptions().apply {
            type = "file"
            format = "yaml"
            config = JsonObject().put("path", "$basePath/config.yaml")
        }

        val options = ConfigRetrieverOptions()
            .addStore(baseConfig)

        val retriever = ConfigRetriever.create(vertx, options)

        return retriever.rxGetConfig()
    }

}