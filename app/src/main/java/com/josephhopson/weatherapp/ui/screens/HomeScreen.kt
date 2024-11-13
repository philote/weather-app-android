package com.josephhopson.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.josephhopson.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun HomeScreen(
    weatherUiState: WeatherUiState
) {
    WeatherView(
        weatherUiState,
    )
}

@Composable
fun WeatherView(
    weatherUiState: WeatherUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = "",
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { /*TODO*/ },
            label = { Text("Enter Your Zip Code") },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /*TODO*/ }
            )
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ })
        {
            Text(
                text = "Submit",
                fontSize = 16.sp
            )
        }
        when(weatherUiState) {
            WeatherUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            WeatherUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
            is WeatherUiState.Success -> ResultScreen(
                weather = weatherUiState.weather,
                modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherViewPreview() {
    WeatherAppTheme {
        WeatherView(WeatherUiState.Loading)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Loading", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Error", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ResultScreen(weather: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = weather)
    }
}