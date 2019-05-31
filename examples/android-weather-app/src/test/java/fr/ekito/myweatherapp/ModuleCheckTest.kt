package fr.ekito.myweatherapp

import android.app.Application
import fr.ekito.myweatherapp.di.offlineWeatherApp
import fr.ekito.myweatherapp.di.onlineWeatherApp
import fr.ekito.myweatherapp.di.roomWeatherApp
import fr.ekito.myweatherapp.di.testWeatherApp
import fr.ekito.myweatherapp.view.detail.DetailViewModel
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.logger.Level
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

/**
 * Test Koin modules
 */
class ModuleCheckTest : KoinTest {

    private val mockedAndroidContext = mock(Application::class.java)
    private val viewModelId = "someId"

    @Test
    fun testRemoteConfiguration() {
        koinApplication {
            fileProperties()
            androidContext(mockedAndroidContext)
            modules(onlineWeatherApp)
        }.checkModules {
            create<DetailViewModel> { parametersOf(viewModelId) }
        }
    }

    @Test
    fun testLocalConfiguration() {
        koinApplication {
            androidContext(mockedAndroidContext)
            modules(offlineWeatherApp)
        }.checkModules {
            create<DetailViewModel> { parametersOf(viewModelId) }
        }
    }

    @Test
    fun testTestConfiguration() {
        koinApplication {
            printLogger(Level.DEBUG)
            androidContext(mockedAndroidContext)
            modules(testWeatherApp)
        }.checkModules {
            create<DetailViewModel> { parametersOf(viewModelId) }
        }
    }

    @Test
    fun testRoomConfiguration() {
        koinApplication {
            androidContext(mockedAndroidContext)
            modules(roomWeatherApp)
        }.checkModules {
            create<DetailViewModel> { parametersOf(viewModelId) }
        }
    }
}
