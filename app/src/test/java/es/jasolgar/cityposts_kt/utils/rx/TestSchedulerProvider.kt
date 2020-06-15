package es.jasolgar.cityposts_kt.utils.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler


class TestSchedulerProvider(testScheduler : TestScheduler) : SchedulerProvider {

    private val mTestScheduler : TestScheduler = testScheduler

    override fun io(): Scheduler? {
        return mTestScheduler
    }

    override fun ui(): Scheduler? {
        return mTestScheduler
    }

}