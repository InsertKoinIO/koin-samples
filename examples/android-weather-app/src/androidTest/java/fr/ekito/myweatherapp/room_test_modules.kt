package fr.ekito.myweatherapp

import androidx.room.Room
import fr.ekito.myweatherapp.data.room.WeatherDatabase
import org.koin.dsl.module

// Room In memroy database
val roomTestModule = module(override = true) {
    single {
        Room.inMemoryDatabaseBuilder(get(), WeatherDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}