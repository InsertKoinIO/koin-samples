package fr.ekito.myweatherapp.di

import fr.ekito.myweatherapp.data.WeatherDataSource
import fr.ekito.myweatherapp.data.local.FileDataSource
import fr.ekito.myweatherapp.data.local.JavaReader
import fr.ekito.myweatherapp.data.local.JsonReader
import fr.ekito.myweatherapp.util.TestSchedulerProvider
import fr.ekito.myweatherapp.util.coroutines.SchedulerProvider
import org.koin.dsl.module


/**
 * Local java json repository
 */
val localJavaDatasourceModule = module {
    single<JsonReader> { JavaReader() }
    single<WeatherDataSource> { FileDataSource(get(), false) }
}

/**
 * Test Rx
 */
val testRxModule = module {
    // provided components
    single<SchedulerProvider> { TestSchedulerProvider() }
}

val testWeatherApp = offlineWeatherApp + testRxModule + localJavaDatasourceModule