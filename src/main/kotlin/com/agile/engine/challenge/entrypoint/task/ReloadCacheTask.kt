package com.agile.engine.challenge.entrypoint.task

import com.agile.engine.challenge.infrastructure.model.TasksConfig
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ReloadCacheTask @Inject constructor(
    val tasksConfig: TasksConfig
) {

    fun execute(): Observable<Long> =
        Observable.interval(tasksConfig.reloadCache.intervalInSeconds, TimeUnit.SECONDS)
            .startWith(1)
            .map {
                println("Executing $it")
                it
            }

}