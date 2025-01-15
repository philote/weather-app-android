package com.josephhopson.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josephhopson.weatherapp.R
import com.josephhopson.weatherapp.model.Forecast
import com.josephhopson.weatherapp.model.Main
import com.josephhopson.weatherapp.model.Weather
import com.josephhopson.weatherapp.ui.AppViewModelProvider
import com.josephhopson.weatherapp.ui.WeatherAppBar
import com.josephhopson.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
    onItemClick: (Forecast) -> Unit,
) {
    val appUiState by viewModel.uiState.collectAsState()
    val weatherUiState = appUiState.weatherUiState

    Scaffold(
        modifier = Modifier,
        topBar = {
            WeatherAppBar(
                title = stringResource(R.string.app_name),
                scrollBehavior = enterAlwaysScrollBehavior(),
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val focusManager = LocalFocusManager.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    value = viewModel.userZipCode,
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    modifier = modifier,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    onValueChange = { viewModel.updateUserZipCode(it) },
                    label = { Text(stringResource(R.string.txt_field_enter_your_zip)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.getWeatherData()
                            focusManager.clearFocus()
                        }
                    )
                )
                Button(
                    modifier = Modifier.padding(top = 8.dp),
                    shape = CircleShape,
                    onClick = {
                        viewModel.getWeatherData()
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.btn_submit)
                    )
                }
            }


            when(weatherUiState) {
                WeatherUiState.Landing -> LandingScreen()
                WeatherUiState.Loading -> LoadingScreen()
                WeatherUiState.Error -> ErrorScreen()
                is WeatherUiState.Success -> ForecastListScreen(
                    forecasts = weatherUiState.fiveDayForecast.forecasts,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun ForecastCard(
    forecast: Forecast,
    modifier: Modifier = Modifier,
    onItemClick: (Forecast) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onItemClick(forecast) },
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
                    text = "${forecast.main.temp}°F"
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
            Text(
                text = forecast.weather.joinToString { it.description }
            )
        }
    }
}

@Composable
fun ForecastListScreen(
    forecasts: List<Forecast>,
    onItemClick: (Forecast) -> Unit,
) {
    LazyColumn {
        items(forecasts) {forecast ->
            ForecastCard(
                forecast = forecast,
                onItemClick = onItemClick
            )
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
            modifier = Modifier.padding(16.dp),
            style=MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
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
            modifier = Modifier
                .padding(16.dp),
            style=MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
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
            modifier = Modifier.padding(16.dp),
            style=MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun ForecastListScreenPreview() {
    WeatherAppTheme {
        ForecastListScreen(
            forecasts = arrayListOf(
                Forecast(
                    main = Main(
                        294.93
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
                Forecast(
                    main = Main(
                        294.93
                    ),
                    dtTxt = "2022-08-30 15:00:00",
                    weather = arrayListOf(
                        Weather(
                            description = "clear sky"
                        )
                    )
                )
            ),
            onItemClick = {}
        )
    }
}

//@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
//@Composable
//fun HomeScreenPreview() {
//    WeatherAppTheme {
//        HomeScreen(
//            onItemClick = {}
//        )
//    }
//}
//
//@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
//@Composable
//fun LoadingScreenPreview() {
//    WeatherAppTheme {
//        LoadingScreen()
//    }
//}
//
//@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
//@Composable
//fun ErrorScreenPreview() {
//    WeatherAppTheme {
//        ErrorScreen()
//    }
//}
