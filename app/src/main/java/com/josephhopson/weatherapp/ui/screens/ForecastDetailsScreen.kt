package com.josephhopson.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josephhopson.weatherapp.R
import com.josephhopson.weatherapp.model.Forecast
import com.josephhopson.weatherapp.model.Main
import com.josephhopson.weatherapp.model.Weather
import com.josephhopson.weatherapp.ui.WeatherAppBar
import com.josephhopson.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun ForecastDetailsScreen(
    forecast: Forecast,
    onItemClick: () -> Unit,
    ) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            WeatherAppBar(
                title = stringResource(R.string.forecast_detail_title),
                scrollBehavior = enterAlwaysScrollBehavior(),
                canNavigateBack = true,
                navigateUp = onItemClick
            )
        }
    ) { innerPadding ->
        val padding = PaddingValues(
            top = innerPadding.calculateTopPadding(),
            bottom = innerPadding.calculateBottomPadding(),
            start = 8.dp,
            end = 8.dp
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Image(
                modifier = Modifier.fillMaxWidth().padding(28.dp),
                painter = painterResource(R.drawable.weather),
                contentDescription = "Weather Icon"
            )
            Row {
                Text(
                    text = "Date: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = forecast.dtTxt)
            }
            Row {
                Text(
                    text = "Temperature: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${forecast.main.temp}°F"
                )
            }
            Row {
                Text(
                    text = "Feels Like: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${forecast.main.feelsLike}°F"
                )
            }
            Row {
                Text(
                    text = "Humidity: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${forecast.main.humidity}%"
                )
            }
            Row {
                Text(
                    text = "Wind: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${forecast.wind.speed} mph"
                )
            }
            Row {
                Text(
                    text = "Wind Gusts: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${forecast.wind.gust} mph"
                )
            }
            Row {
                Text(
                    text = "Cloud Cover ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${forecast.clouds.all}%"
                )
            }
            Text(
                text = forecast.weather.joinToString { it.description }
            )
        }
    }
}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun WeatherAppPreview() {
    WeatherAppTheme {
        ForecastDetailsScreen(
            Forecast(
                main = Main(
                    94.93
                ),
                dtTxt = "2022-08-30 15:00:00",
                weather = arrayListOf(
                    Weather(
                        description = "clear sky"
                    ),
                    Weather(
                        description = "overcast clouds"
                    )
                )
            ),
            onItemClick = {}
        )
    }
}