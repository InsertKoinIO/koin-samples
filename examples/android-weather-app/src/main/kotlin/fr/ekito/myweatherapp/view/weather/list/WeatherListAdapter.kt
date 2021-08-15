package fr.ekito.myweatherapp.view.weather.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.ekito.myweatherapp.databinding.ItemWeatherBinding
import fr.ekito.myweatherapp.domain.entity.getColorFromCode

class WeatherListAdapter(
    private val context: Context,
    var list: List<WeatherItem>,
    private val onDetailSelected: (WeatherItem) -> Unit
) : RecyclerView.Adapter<WeatherListAdapter.WeatherResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherResultHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherResultHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherResultHolder, position: Int) {
        holder.display(list[position], context, onDetailSelected)
    }

    override fun getItemCount() = list.size

    inner class WeatherResultHolder(
        private val binding: ItemWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun display(
            dailyForecastModel: WeatherItem,
            context: Context,
            onClick: (WeatherItem) -> Unit
        ) {
            with(binding) {
                weatherItemLayout.setOnClickListener { onClick(dailyForecastModel) }
                weatherItemDay.text = dailyForecastModel.day
                weatherItemIcon.text = dailyForecastModel.icon
                val color = context.getColorFromCode(dailyForecastModel)
                weatherItemDay.setTextColor(color)
                weatherItemIcon.setTextColor(color)
            }
        }

    }
}