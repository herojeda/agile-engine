package com.agile.engine.challenge.entrypoint.task

import com.agile.engine.challenge.core.usecase.ReloadCache
import com.agile.engine.challenge.infrastructure.model.TasksConfig
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ReloadCacheTask @Inject constructor(
    val tasksConfig: TasksConfig,
    val reloadCache: ReloadCache
) {

    fun execute(): Observable<Long> =
        Observable.interval(tasksConfig.reloadCache.intervalInSeconds, TimeUnit.SECONDS)
            .startWith(1)
            .flatMap {
                reloadCache.execute()
                    .andThen(Observable.just(it))
            }

}