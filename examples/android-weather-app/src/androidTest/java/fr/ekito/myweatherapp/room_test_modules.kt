package fr.ekito.myweatherapp

import androidx.room.Room
import fr.ekito.myweatherapp.data.room.WeatherDatabase
import org.koin.dsl.module

// Room In memory database
val roomTestModule = module {
    single {
        Room.inMemoryDatabaseBuilder(get(), WeatherDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}