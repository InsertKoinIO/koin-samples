package fr.ekito.myweatherapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single
import java.util.Date

@Dao
interface WeatherDAO {

    @Insert
    fun saveAll(entities: List<WeatherEntity>)

    @Query("SELECT * FROM weather WHERE id = :id")
    fun findWeatherById(id: String): Single<WeatherEntity>

    @Query("SELECT * FROM weather WHERE location = :location AND date = :date")
    fun findAllBy(location: String, date: Date): Single<List<WeatherEntity>>

    @Query("SELECT * FROM weather ORDER BY date DESC")
    fun findLatestWeather(): Single<List<WeatherEntity>>
}