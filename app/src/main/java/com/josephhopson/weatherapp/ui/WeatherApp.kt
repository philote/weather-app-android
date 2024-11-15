package com.josephhopson.weatherapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.josephhopson.weatherapp.R
import com.josephhopson.weatherapp.model.Forecast
import com.josephhopson.weatherapp.ui.screens.ForecastDetailsScreen
import com.josephhopson.weatherapp.ui.screens.HomeScreen
import com.josephhopson.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun WeatherApp() {
    // this navigator is used to move between list and details screens
    val navigator = rememberListDetailPaneScaffoldNavigator<Forecast>()

    // adds support for navigating back from the detail screen
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    // Scaffold to add a super basic App Bar
    Scaffold(
        modifier = Modifier,
        topBar = { WeatherAppBar() }
    ) { padding ->
        ListDetailPaneScaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    HomeScreen(
                        onItemClick = { forecast ->
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, forecast)
                        }
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    navigator.currentDestination?.content?.let { forecast ->
                        ForecastDetailsScreen(forecast)
                    }
                }
            },
        )
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
