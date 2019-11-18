package com.sample.weather.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.annotation.GlideModule
import com.sample.weather.R
import com.sample.weather.internal.glide.GlideApp
import com.sample.weather.ui.base.ScopedFragment
import com.sample.weather.vi.Resource
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    val viewModel: CurrentWeatherViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            when (it) {
                is Resource.Success -> {
                    val currentWeatherEntity = it.data!!
                    Log.d("Awasthi", "Success!!")
                    textView_feels_like_temperature.text =
                        currentWeatherEntity.feelslike.toString()
                    group_loading.visibility = View.GONE
                    updateDataToToday()
                    updateTemperature(
                        currentWeatherEntity.temperature,
                        currentWeatherEntity.feelslike
                    )
                    updateCondition(currentWeatherEntity.weatherDescriptions.get(0))
                    updatePrecipitation(currentWeatherEntity.precip)
                    updateWind(currentWeatherEntity.windDir, currentWeatherEntity.windSpeed)
                    updateVisibility(currentWeatherEntity.visibility)
                    GlideApp.with(this@CurrentWeatherFragment)
                        .load(currentWeatherEntity.weatherIcons.get(0))
                        .into(imageView_condition_icon)
                }
                is Resource.Loading -> {
                    Log.d("Awasthi", "Loading!!")
                    group_loading.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun updateDataToToday() {
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double, feelsLike: Double) {
        val unitAbbreviations = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviations"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviations"
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Preciptiation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }
}