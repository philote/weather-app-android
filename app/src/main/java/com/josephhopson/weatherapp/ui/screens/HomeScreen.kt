package com.josephhopson.weatherapp.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josephhopson.weatherapp.R
import com.josephhopson.weatherapp.model.Forecast
import com.josephhopson.weatherapp.model.Main
import com.josephhopson.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun HomeScreen(
    weatherViewModel: WeatherViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()
    val weatherApiUiState = weatherUIState.weatherUiState
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = weatherViewModel.userZipCode,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { weatherViewModel.updateUserZipCode(it) },
            label = { Text(stringResource(R.string.txt_field_enter_your_zip)) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    weatherViewModel.getWeatherData()
                    focusManager.clearFocus()
                }
            )
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                weatherViewModel.getWeatherData()
                focusManager.clearFocus()
            })
        {
            Text(
                text = stringResource(R.string.btn_submit),
                fontSize = 16.sp
            )
        }
        when(weatherApiUiState) {
            WeatherUiState.Landing -> LandingScreen()
            WeatherUiState.Loading -> LoadingScreen()
            WeatherUiState.Error -> ErrorScreen()
            is WeatherUiState.Success -> ForecastListScreen(
                forecasts = weatherApiUiState.fiveDayForecast.forecasts
            )
        }
    }
}

@Composable
fun ForecastCard(
    forecast: Forecast,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
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

@Composable
fun ForecastListScreen(
    forecasts: List<Forecast>,
) {
    LazyColumn {
        items(forecasts) {forecast ->
            ForecastCard(forecast)
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
        Text(
            text = stringResource(R.string.loading),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun LandingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(R.drawable.weather),
            contentDescription = stringResource(R.string.enter_your_zip_code_above)
        )
        Text(
            text = stringResource(R.string.enter_your_zip_code_above),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(R.drawable.error),
            contentDescription = stringResource(R.string.error)
        )
        Text(
            text = stringResource(R.string.error),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun ForecastListScreenPreview() {
    WeatherAppTheme {
        ForecastListScreen(
            arrayListOf(
                Forecast(
                    dt = 123,
                    main = Main(
                        294.93.converterKelvinToFahrenheit()
                    ),
                    dtTxt = "2022-08-30 15:00:00"
                )
            )
        )
    }
}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    WeatherAppTheme {
        HomeScreen()
    }
}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun LoadingScreenPreview() {
    WeatherAppTheme {
        LoadingScreen()
    }
}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun ErrorScreenPreview() {
    WeatherAppTheme {
        ErrorScreen()
    }
}
