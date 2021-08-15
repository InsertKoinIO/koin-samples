package fr.ekito.myweatherapp

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import fr.ekito.myweatherapp.domain.repository.DailyForecastRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4ClassRunner::class)
class WeatherRepositoryTest : KoinTest {

    private val weatherRepository: DailyForecastRepository by inject()

    @Before
    fun before() {
        loadKoinModules(roomTestModule)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testGetDefault() {
        val defaultWeather = weatherRepository.getWeather().blockingGet()
        val defaultWeather2 = weatherRepository.getWeather().blockingGet()
        assertEquals(defaultWeather, defaultWeather2)
    }

    @Test
    fun testGetWeatherDetail() {
        val defaultWeather = weatherRepository.getWeather().blockingGet()

        val result = defaultWeather.first()
        val first = weatherRepository.getWeatherDetail(result.id).blockingGet()
        assertEquals(result, first)
    }

    @Test
    fun testGetLatest() {
        weatherRepository.getWeather().blockingGet()
        weatherRepository.getWeather("London").blockingGet()
        val toulouse = weatherRepository.getWeather("Toulouse").blockingGet()
        val defaultWeather3 = weatherRepository.getWeather().blockingGet()
        assertEquals(defaultWeather3, toulouse)
    }
}