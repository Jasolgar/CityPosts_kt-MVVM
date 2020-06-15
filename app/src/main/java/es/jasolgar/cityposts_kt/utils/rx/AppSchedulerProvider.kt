package es.jasolgar.cityposts_kt.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulerProvider : SchedulerProvider {

    override fun io(): Scheduler? { return Schedulers.io() }

    override fun ui(): Scheduler? { return AndroidSchedulers.mainThread() }
}