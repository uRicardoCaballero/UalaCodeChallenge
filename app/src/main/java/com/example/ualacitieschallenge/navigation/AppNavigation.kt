package com.example.ualacitieschallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ualacitieschallenge.ui.screens.CityDetailScreen
import com.example.ualacitieschallenge.ui.screens.MainScreen
import com.example.ualacitieschallenge.ui.viewmodel.CityViewModel

@Composable
fun AppNavigation(viewModel: CityViewModel) {
    // Create a NavController
    val navController = rememberNavController()

    // Define the navigation graph
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route // Set the starting screen
    ) {
        // MainScreen route
        composable(Screen.MainScreen.route) {
            MainScreen(
                viewModel = viewModel,
                onCitySelected = { city ->
                    // Navigate to the CityDetailScreen when a city is selected
                    // Convert city.id to Long explicitly
                    navController.navigate(Screen.CityDetailScreen.createRoute(city.id.toLong()))
                }
            )
        }

        // CityDetailScreen route
        composable(
            route = Screen.CityDetailScreen.route,
            arguments = Screen.CityDetailScreen.arguments
        ) { backStackEntry ->
            // Retrieve the cityId from the back stack entry
            val cityId = backStackEntry.arguments?.getLong("cityId")
            if (cityId != null) {
                // Fetch the city details from the ViewModel
                val city = viewModel.getCityById(cityId)
                if (city != null) {
                    CityDetailScreen(
                        city = city,
                        viewModel = viewModel,
                        onBackPressed = {
                            // Navigate back to the MainScreen
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

// Define the screens as sealed classes
sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object CityDetailScreen : Screen("city_detail_screen/{cityId}") {
        fun createRoute(cityId: Long) = "city_detail_screen/$cityId"

        val arguments = listOf(
            navArgument("cityId") {
                type = androidx.navigation.NavType.LongType
            }
        )
    }
}