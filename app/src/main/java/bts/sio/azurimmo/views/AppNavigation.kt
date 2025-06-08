package bts.sio.azurimmo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import bts.sio.azurimmo.views.*

import bts.sio.azurimmo.views.contrat.AjouterContratScreen
import bts.sio.azurimmo.views.garant.GarantDetailScreen
import bts.sio.azurimmo.views.intervention.AjouterInterventionScreen
import bts.sio.azurimmo.views.intervention.InterventionDetailScreen
import bts.sio.azurimmo.views.locataire.LocataireDetailScreen
import bts.sio.azurimmo.views.paiement.PaiementDetailScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToBatiments = { navController.navigate("batiments") },
                onNavigateToAppartements = { navController.navigate("appartements") },
                onNavigateToContrats = { navController.navigate("contrats") },
                onNavigateToInterventions = { navController.navigate("interventions") },
                onNavigateToGarants = { navController.navigate("garants") },
                onNavigateToLocataires = { navController.navigate("locataires") },
                onNavigateToPaiements = { navController.navigate("paiements") }
            )
        }

        // 📦 Routes avec paramètres (pages de détails)
        composable("batiment/{batimentId}") { backStackEntry ->
            backStackEntry.arguments?.getString("batimentId")?.toLongOrNull()?.let {
                BatimentDetailScreen(navController = navController, batimentId = it)
            }
        }

        composable("appartement/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.toLongOrNull()?.let {
                AppartementDetailScreen(navController = navController, appartementId = it)
            }
        }

        composable("contrat/{contratId}") { backStackEntry ->
            backStackEntry.arguments?.getString("contratId")?.toLongOrNull()?.let {
                ContratDetailScreen(navController = navController, contratId = it)
            }
        }

        composable("intervention/{interventionId}") { backStackEntry ->
            backStackEntry.arguments?.getString("interventionId")?.toLongOrNull()?.let {
                InterventionDetailScreen(navController = navController, interventionId = it)
            }
        }

        composable("garant/{garantId}") { backStackEntry ->
            backStackEntry.arguments?.getString("garantId")?.toLongOrNull()?.let {
                GarantDetailScreen(navController = navController, garantId = it)
            }
        }

        composable("locataire/{locataireId}") { backStackEntry ->
            backStackEntry.arguments?.getString("locataireId")?.toLongOrNull()?.let {
                LocataireDetailScreen(navController = navController, locataireId = it)
            }
        }

        composable("paiement/{paiementId}") { backStackEntry ->
            backStackEntry.arguments?.getString("paiementId")?.toLongOrNull()?.let {
                PaiementDetailScreen(navController = navController, paiementId = it)
            }
        }

        // 📄 Routes principales (listes)
        composable("batiments") { BatimentScreen(navController = navController) }
        composable("appartements") { AppartementScreen(navController = navController) }
        composable("contrats") { ContratScreen(navController = navController) }
        composable("interventions") { InterventionScreen(navController = navController) }
        composable("garants") { GarantScreen(navController = navController) }
        composable("locataires") { LocataireScreen(navController = navController) }
        composable("paiements") { PaiementScreen(navController = navController) }

        // ➕ Ajout depuis un appartement
        composable("ajouterContrat/{appartementId}") { backStackEntry ->
            backStackEntry.arguments?.getString("appartementId")?.toLongOrNull()?.let {
                AjouterContratScreen(appartementId = it, navController = navController)
            }
        }

        composable("ajouterIntervention/{appartementId}") { backStackEntry ->
            backStackEntry.arguments?.getString("appartementId")?.toLongOrNull()?.let {
                AjouterInterventionScreen(appartementId = it, navController = navController)
            }
        }
    }
}