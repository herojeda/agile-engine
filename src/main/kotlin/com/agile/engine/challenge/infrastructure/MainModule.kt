package com.agile.engine.challenge.infrastructure

import com.agile.engine.challenge.core.repository.auth.ObtainAuthTokenRepository
import com.agile.engine.challenge.core.repository.images.GetImagesRepository
import com.agile.engine.challenge.infrastructure.model.ClientConfig
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import com.agile.engine.challenge.infrastructure.model.ConfigurationModel
import com.agile.engine.challenge.infrastructure.model.SystemConfig
import com.agile.engine.challenge.infrastructure.model.TasksConfig
import com.agile.engine.challenge.infrastructure.routers.BaseRouter
import com.agile.engine.challenge.repository.rest.auth.GetImagesRestRepository
import com.agile.engine.challenge.repository.rest.auth.ObtainAuthTokenRestRepository
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.client.WebClient

class MainModule(private val vertx: Vertx, private val config: JsonObject) : AbstractModule() {

    companion object {
        fun webClient(vertx: Vertx, size: Int): WebClient = WebClient.create(
            vertx,
            WebClientOptions()
                .setSsl(true)
                .setTrustAll(true)
                .setDefaultPort(443)
                .setKeepAlive(true)
                .setMaxPoolSize(size)
        )
    }

    override fun configure() {

        // Config
        val configModel = config.mapTo(ConfigurationModel::class.java)
        bind(SystemConfig::class.java).toInstance(configModel.system)
        bind(TasksConfig::class.java).toInstance(configModel.tasks)
        bind(ClientConfig::class.java).toInstance(configModel.client)

        // Main
        bind(Vertx::class.java).toInstance(vertx)
        bind(MainVerticle::class.java).asEagerSingleton()

        // WebClient
        bind(WebClient::class.java).toInstance(webClient(vertx, 2500))

        // Repositories
        bind(ObtainAuthTokenRepository::class.java).to(ObtainAuthTokenRestRepository::class.java)
        bind(GetImagesRepository::class.java).to(GetImagesRestRepository::class.java)

        val routerBinder = Multibinder.newSetBinder(binder(), BaseRouter::class.java)
    }
}