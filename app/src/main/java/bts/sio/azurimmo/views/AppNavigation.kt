package bts.sio.azurimmo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import bts.sio.azurimmo.views.BatimentScreen
import bts.sio.azurimmo.views.AppartementScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "appartements", // ou "appartements" selon ce que tu veux tester en premier
        modifier = modifier
    ) {
        composable("batiments") {
            BatimentScreen()
        }

        composable("appartements") {
            AppartementScreen()
        }
    }
}