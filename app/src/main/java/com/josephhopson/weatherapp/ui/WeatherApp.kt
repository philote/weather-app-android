package com.josephhopson.weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.josephhopson.weatherapp.R
import com.josephhopson.weatherapp.ui.screens.HomeScreen
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
            HomeScreen()
        }
    }
}

@Composable
fun WeatherAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun WeatherAppPreview() {
    WeatherAppTheme {
        WeatherApp()
    }
}