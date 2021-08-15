package fr.ekito.myweatherapp.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.ekito.myweatherapp.R
import fr.ekito.myweatherapp.databinding.ActivityDetailBinding
import fr.ekito.myweatherapp.domain.entity.DailyForecast
import fr.ekito.myweatherapp.domain.entity.getColorFromCode
import fr.ekito.myweatherapp.util.android.argument
import fr.ekito.myweatherapp.view.Failed
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Weather Detail View
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    // Get all needed data
    private val detailId by argument<String>(INTENT_WEATHER_ID)

    private val detailViewModel: DetailViewModel by viewModel { parametersOf(detailId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.states.observe(this, { state ->
            when (state) {
                is Failed -> showError(state.error)
                is DetailViewModel.DetailLoaded -> showDetail(state.weather)
            }
        })
        detailViewModel.getDetail()
    }

    private fun showError(error: Throwable) {
        Snackbar.make(
                binding.weatherItem,
                getString(R.string.loading_error) + " - $error",
                Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showDetail(weather: DailyForecast) {
        with(binding) {
            weatherIcon.text = weather.icon
            weatherDay.text = weather.day
            weatherText.text = weather.fullText
            weatherWindText.text = weather.wind.toString()
            weatherTempText.text = weather.temperature.toString()
            weatherHumidityText.text = weather.humidity.toString()
            weatherItem.background.setTint(getColorFromCode(weather))
            // Set back on background click
            weatherItem.setOnClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        const val INTENT_WEATHER_ID: String = "INTENT_WEATHER_ID"
    }
}
