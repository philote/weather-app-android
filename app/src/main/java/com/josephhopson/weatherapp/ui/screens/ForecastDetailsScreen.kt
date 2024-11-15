package com.josephhopson.weatherapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josephhopson.weatherapp.model.Forecast
import com.josephhopson.weatherapp.model.Main
import com.josephhopson.weatherapp.ui.theme.WeatherAppTheme


@Composable
fun ForecastDetailsScreen(forecast: Forecast) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
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
                    text = "${forecast.main.temp.converterKelvinToFahrenheit()}°F"
                )
            }
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
                    294.93.converterKelvinToFahrenheit()
                ),
                dtTxt = "2022-08-30 15:00:00"
            )
        )
    }
}