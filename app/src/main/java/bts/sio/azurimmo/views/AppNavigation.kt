package bts.sio.azurimmo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import bts.sio.azurimmo.views.BatimentScreen
import bts.sio.azurimmo.views.AppartementScreen
import bts.sio.azurimmo.views.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home", // 👈 très important !
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToBatiments = { navController.navigate("batiments") },
                onNavigateToAppartements = { navController.navigate("appartements") }
            )
        }

        composable("batiments") {
            BatimentScreen(navController = navController)
        }

        composable("appartements") {
            AppartementScreen(navController = navController)
        }
    }
}