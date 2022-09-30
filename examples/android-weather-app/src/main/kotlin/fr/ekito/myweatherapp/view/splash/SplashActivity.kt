package fr.ekito.myweatherapp.view.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.ekito.myweatherapp.R
import fr.ekito.myweatherapp.databinding.ActivitySplashBinding
import fr.ekito.myweatherapp.view.Error
import fr.ekito.myweatherapp.view.Pending
import fr.ekito.myweatherapp.view.Success
import fr.ekito.myweatherapp.view.weather.WeatherActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Search Weather View
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel.events.observe(this, { event ->
            when (event) {
                is Pending -> showIsLoading()
                is Success -> showIsLoaded()
                is Error -> showError(event.error)
            }
        })
        splashViewModel.getLastWeather()
    }

    private fun showIsLoading() {
        val animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.infinite_blinking_animation)
        binding.splashIcon.startAnimation(animation)
    }

    private fun showIsLoaded() {
        startActivity(
            Intent(this, WeatherActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }

    private fun showError(error: Throwable) {
        with(binding) {
            splashIcon.visibility = View.GONE
            splashIconFail.visibility = View.VISIBLE
            Snackbar.make(splash, "SplashActivity got error : $error", Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    splashViewModel.getLastWeather()
                }
                .show()
        }
    }
}
