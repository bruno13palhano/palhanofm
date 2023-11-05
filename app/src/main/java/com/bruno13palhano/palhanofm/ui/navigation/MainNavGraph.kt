package com.bruno13palhano.palhanofm.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bruno13palhano.palhanofm.ui.screens.HomeScreen
import com.bruno13palhano.palhanofm.ui.screens.SchedulesScreen
import com.bruno13palhano.palhanofm.ui.screens.SettingsScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = MainDestinations.HOME_ROUTE,
    showBottomMenu: (show: Boolean) -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = MainDestinations.HOME_ROUTE) {
            showBottomMenu(true)
            HomeScreen()
        }

        composable(route = MainDestinations.SCHEDULES_ROUTE) {
            showBottomMenu(true)
            SchedulesScreen()
        }

        composable(route = MainDestinations.SETTINGS_ROUTE) {
            showBottomMenu(false)
            SettingsScreen()
        }
    }
}

object MainDestinations {
    const val HOME_ROUTE = "home_route"
    const val SCHEDULES_ROUTE = "schedules_route"
    const val SETTINGS_ROUTE = "settings_route"
}