package fr.ekito.myweatherapp

import android.app.Application
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.WeathericonsModule
import fr.ekito.myweatherapp.di.roomWeatherApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Main Application
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin {
            fileProperties()
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(roomWeatherApp)
        }

        Iconify.with(WeathericonsModule())
    }
}
