package bts.sio.azurimmo.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import bts.sio.azurimmo.views.appartement.AppartementList
import bts.sio.azurimmo.views.batiment.BatimentList

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "batiment_list",
        modifier = modifier
    ) {
        composable("batiment_list") {
            BatimentList(
                onBatimentClick = { batimentId ->
                    navController.navigate("batiment_appartements_list/$batimentId")
                }
            )
        }
        composable(
            route = "batiment_appartements_list/{batimentId}",
            arguments = listOf(navArgument("batimentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val batimentId = backStackEntry.arguments?.getInt("batimentId")
            if (batimentId != null) {
                AppartementList(batimentId = batimentId)
            } else {
                Text("Erreur : Identifiant de bâtiment manquant")
            }
        }
    }
}