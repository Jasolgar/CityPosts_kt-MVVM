package es.jasolgar.cityposts_kt.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler?

    fun ui(): Scheduler?

}