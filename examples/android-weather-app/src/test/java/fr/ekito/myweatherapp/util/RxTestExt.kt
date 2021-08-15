package fr.ekito.myweatherapp.util

import io.reactivex.rxjava3.observers.TestObserver

fun <T> TestObserver<T>.awaitTerminalEvent(): Boolean {
    return try {
        this.await()
        true
    } catch (ex: InterruptedException) {
        Thread.currentThread().interrupt()
        false
    }
}