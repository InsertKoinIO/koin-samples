package fr.ekito.myweatherapp.data.local

import fr.ekito.myweatherapp.data.WeatherDataSource
import fr.ekito.myweatherapp.data.json.Geocode
import fr.ekito.myweatherapp.data.json.Weather
import io.reactivex.Single
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Read json files and render weather date
 */
class FileDataSource(private val jsonReader: JsonReader, private val delayed: Boolean) :
    WeatherDataSource {

    private val cities by lazy { jsonReader.getAllLocations() }

    private fun isKnownCity(address: String): Boolean = cities.values.contains(address)

    private fun cityFromLocation(lat: Double?, lng: Double?): String {
        return cities.filterKeys { it.lat == lat && it.lng == lng }.values.firstOrNull()
            ?: DEFAULT_CITY
    }

    override fun geocode(address: String): Single<Geocode> {
        val single = Single.create<Geocode> { s ->
            val addressToLC = address.lowercase(Locale.getDefault())
            val geocode = if (isKnownCity(addressToLC)) {
                jsonReader.getGeocode(addressToLC)
            } else {
                jsonReader.getGeocode(DEFAULT_CITY)
            }
            s.onSuccess(geocode)
        }
        return if (delayed) single.delay(1, TimeUnit.SECONDS) else single
    }

    override fun weather(lat: Double?, lon: Double?, lang: String): Single<Weather> {
        val single = Single.create<Weather> { s ->
            val city = cityFromLocation(lat, lon)
            val weather = jsonReader.getWeather(city)
            s.onSuccess(weather)
        }
        return if (delayed) single.delay(1, TimeUnit.SECONDS) else single
    }

    companion object {
        const val DEFAULT_CITY = "toulouse"
    }
}