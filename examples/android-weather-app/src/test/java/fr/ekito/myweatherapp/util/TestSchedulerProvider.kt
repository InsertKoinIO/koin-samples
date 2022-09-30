package fr.ekito.myweatherapp.util

import io.reactivex.rxjava3.schedulers.Schedulers
import fr.ekito.myweatherapp.util.coroutines.SchedulerProvider
import io.reactivex.rxjava3.core.Scheduler

class TestSchedulerProvider : SchedulerProvider {
    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun computation(): Scheduler = Schedulers.trampoline()
}