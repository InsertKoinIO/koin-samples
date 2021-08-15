package fr.ekito.myweatherapp.view.weather

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fr.ekito.myweatherapp.databinding.FragmentResultListBinding
import fr.ekito.myweatherapp.view.detail.DetailActivity
import fr.ekito.myweatherapp.view.weather.list.WeatherItem
import fr.ekito.myweatherapp.view.weather.list.WeatherListAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeatherListFragment : Fragment() {

    private var _binding: FragmentResultListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareListView()
        viewModel.states.observe(viewLifecycleOwner, { state ->
            when (state) {
                is WeatherViewModel.WeatherListLoaded -> showWeatherItemList(state.lasts.map {
                    WeatherItem.from(it)
                })
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareListView() {
        with(binding) {
            weatherList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            weatherList.adapter = WeatherListAdapter(
                requireActivity(),
                emptyList(),
                ::onWeatherItemSelected
            )
        }
    }

    private fun onWeatherItemSelected(resultItem: WeatherItem) {
        activity?.startActivity(
            Intent(context, DetailActivity::class.java).putExtra(
                DetailActivity.INTENT_WEATHER_ID,
                resultItem.id
            )
        )
    }

    private fun showWeatherItemList(newList: List<WeatherItem>) {
        val adapter: WeatherListAdapter = binding.weatherList.adapter as WeatherListAdapter
        adapter.list = newList
        adapter.notifyDataSetChanged()
    }
}