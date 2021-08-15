package fr.ekito.myweatherapp.view.splash

import androidx.lifecycle.LiveData
import fr.ekito.myweatherapp.domain.repository.DailyForecastRepository
import fr.ekito.myweatherapp.util.mvvm.RxViewModel
import fr.ekito.myweatherapp.util.mvvm.SingleLiveEvent
import fr.ekito.myweatherapp.util.coroutines.SchedulerProvider
import fr.ekito.myweatherapp.util.coroutines.with
import fr.ekito.myweatherapp.view.Error
import fr.ekito.myweatherapp.view.Pending
import fr.ekito.myweatherapp.view.Success
import fr.ekito.myweatherapp.view.ViewModelEvent

class SplashViewModel(
    private val dailyForecastRepository: DailyForecastRepository,
    private val schedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val _events = SingleLiveEvent<ViewModelEvent>()
    val events: LiveData<ViewModelEvent>
        get() = _events

    fun getLastWeather() {
        _events.value = Pending
        launch {
            dailyForecastRepository.getWeather().with(schedulerProvider)
                .ignoreElement()
                .subscribe(
                    { _events.value = Success },
                    { error -> _events.value = Error(error) })
        }
    }
}