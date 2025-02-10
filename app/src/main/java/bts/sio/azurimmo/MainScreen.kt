package bts.sio.azurimmo

import AppartementList
import BatimentList
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "batiment_list") {

        composable("batiment_list") {
            BatimentList(
                onBatimentClick = { batimentId: Int -> // Ajout du type explicite Int
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