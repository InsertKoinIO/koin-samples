package fr.ekito.myweatherapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.ekito.myweatherapp.domain.entity.DailyForecast
import java.util.Date

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val id: String,
    val location: String,
    val day: String,
    val shortText: String,
    val fullText: String,
    val iconUrl: String,
    val icon: String,
    val temp_low: String,
    val temp_high: String,
    val wind_kph: Int,
    val wind_dir: String,
    val humidity: Int,
    val date: Date
) {
    companion object {
        fun from(model: DailyForecast, date: Date) = WeatherEntity(
            model.id,
            model.location,
            model.day,
            model.shortText,
            model.fullText,
            model.iconUrl,
            model.icon,
            model.temperature.low,
            model.temperature.high,
            model.wind.kph,
            model.wind.dir,
            model.humidity.humidity,
            date
        )
    }
}
