package com.josephhopson.weatherapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
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

    ListDetailPaneScaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                    ForecastDetailsScreen(
                        forecast = forecast,
                        onItemClick = {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.List)
                        }
                    )
                }
            }
        },
    )
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@Composable
fun WeatherAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(
            title,
            style = MaterialTheme.typography.headlineSmall,
            ) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun WeatherAppPreview() {
    WeatherAppTheme {
        WeatherApp()
    }
}
