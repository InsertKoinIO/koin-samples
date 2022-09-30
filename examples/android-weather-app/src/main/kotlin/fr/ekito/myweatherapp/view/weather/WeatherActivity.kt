package fr.ekito.myweatherapp.view.weather

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.ekito.myweatherapp.R
import fr.ekito.myweatherapp.databinding.ActivityResultBinding
import fr.ekito.myweatherapp.view.Failed
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Weather Result View
 */
class WeatherActivity : AppCompatActivity() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private lateinit var binding: ActivityResultBinding

    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weatherTitleFragment = WeatherHeaderFragment()
        val resultListFragment = WeatherListFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_title, weatherTitleFragment)
            .commit()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_list, resultListFragment)
            .commit()

        viewModel.states.observe(this, { state ->
            when (state) {
                is Failed -> showError(state.error)
            }
        })
        viewModel.getWeather()

    }

    private fun showError(error: Throwable) {
        Log.e(TAG, "error $error while displaying weather")
        with(binding) {
            weatherViews.visibility = View.GONE
            weatherError.visibility = View.VISIBLE
            Snackbar.make(
                weatherResult,
                "WeatherActivity got error : $error",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.retry) {
                    startActivity(
                        Intent(this@WeatherActivity, WeatherActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                    )
                }
                .show()
        }
    }
}
