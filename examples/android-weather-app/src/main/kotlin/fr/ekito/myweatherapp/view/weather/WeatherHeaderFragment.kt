package fr.ekito.myweatherapp.view.weather

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import fr.ekito.myweatherapp.R
import fr.ekito.myweatherapp.databinding.FragmentResultHeaderBinding
import fr.ekito.myweatherapp.domain.entity.DailyForecast
import fr.ekito.myweatherapp.domain.entity.getColorFromCode
import fr.ekito.myweatherapp.view.detail.DetailActivity
import fr.ekito.myweatherapp.view.detail.DetailActivity.Companion.INTENT_WEATHER_ID
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeatherHeaderFragment : Fragment() {

    private var _binding: FragmentResultHeaderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultHeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.states.observe(viewLifecycleOwner, { state ->
            when (state) {
                is WeatherViewModel.WeatherListLoaded -> showWeather(state.location, state.first)
            }
        })
        viewModel.events.observe(viewLifecycleOwner, { event ->
            when (event) {
                is WeatherViewModel.ProceedLocation -> showLoadingLocation(event.location)
                is WeatherViewModel.ProceedLocationError -> showLocationSearchFailed(
                    event.location,
                    event.error
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showWeather(location: String, weather: DailyForecast) {
        with(binding) {
            weatherCity.text = location
            weatherCityCard.setOnClickListener {
                promptLocationDialog()
            }

            weatherIcon.text = weather.icon
            weatherDay.text = weather.day
            weatherTempText.text = weather.temperature.toString()
            weatherText.text = weather.shortText

            val color = requireContext().getColorFromCode(weather)
            weatherHeader.background.setTint(color)

            weatherHeader.setOnClickListener {
                activity?.startActivity(
                    Intent(context, DetailActivity::class.java).putExtra(
                        INTENT_WEATHER_ID,
                        weather.id
                    )
                )
            }
        }
    }

    private fun promptLocationDialog() {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(getString(R.string.enter_location))
        val editText = EditText(context)
        editText.hint = getString(R.string.location_hint)
        editText.maxLines = 1
        editText.inputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
        dialog.setView(editText)
        dialog.setPositiveButton(getString(R.string.search)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            val newLocation = editText.text.trim().toString()
            viewModel.loadNewLocation(newLocation)
        }
        dialog.setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        dialog.show()
    }

    private fun showLoadingLocation(location: String) {
        Snackbar.make(
            binding.weatherHeader,
            getString(R.string.loading_location) + " $location ...",
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    private fun showLocationSearchFailed(location: String, error: Throwable) {
        Snackbar.make(binding.weatherHeader, getString(R.string.loading_error), Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) {
                viewModel.loadNewLocation(location)
            }
            .show()
    }
}