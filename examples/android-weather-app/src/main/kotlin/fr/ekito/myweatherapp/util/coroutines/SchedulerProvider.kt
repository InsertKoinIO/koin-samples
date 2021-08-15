package fr.ekito.myweatherapp.util.coroutines

import io.reactivex.rxjava3.core.Scheduler

/**
 * Rx Scheduler Provider
 */
interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}