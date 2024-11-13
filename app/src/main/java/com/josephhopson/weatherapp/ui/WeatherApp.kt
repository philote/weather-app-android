@file:OptIn(ExperimentalMaterial3Api::class)

package com.josephhopson.weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josephhopson.weatherapp.ui.screens.HomeScreen
import com.josephhopson.weatherapp.ui.screens.WeatherViewModel
import com.josephhopson.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherApp() {
    Scaffold(
        modifier = Modifier,
        topBar = { WeatherAppBar() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val weatherViewModel: WeatherViewModel = viewModel()
            HomeScreen(
                weatherUiState = weatherViewModel.weatherUiState,
            )
        }
    }
}

@Composable
fun WeatherAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "Weather App",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun WeatherAppPreview() {
    WeatherAppTheme {
        WeatherApp()
    }
}