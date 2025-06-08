package bts.sio.azurimmo.views.contrat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bts.sio.azurimmo.viewmodel.ContratViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjouterContratScreen(
    appartementId: Long,
    navController: NavHostController,
    contratViewModel: ContratViewModel = viewModel()
) {
    var dateEntree by remember { mutableStateOf("") }
    var dateSortie by remember { mutableStateOf("") }
    var montantLoyer by remember { mutableStateOf("") }
    var montantCharges by remember { mutableStateOf("") }
    var statut by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajouter un contrat") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = dateEntree, onValueChange = { dateEntree = it }, label = { Text("Date d'entrée") })
            OutlinedTextField(value = dateSortie, onValueChange = { dateSortie = it }, label = { Text("Date de sortie") })
            OutlinedTextField(value = montantLoyer, onValueChange = { montantLoyer = it }, label = { Text("Montant loyer") })
            OutlinedTextField(value = montantCharges, onValueChange = { montantCharges = it }, label = { Text("Montant charges") })
            OutlinedTextField(value = statut, onValueChange = { statut = it }, label = { Text("Statut") })

            Button(onClick = {
                if (dateEntree.isNotBlank() && dateSortie.isNotBlank() && montantLoyer.isNotBlank() && montantCharges.isNotBlank() && statut.isNotBlank()) {
                    contratViewModel.addContrat(
                        dateEntree = dateEntree,
                        dateSortie = dateSortie,
                        montantLoyer = montantLoyer,
                        montantCharges = montantCharges,
                        statut = statut,
                        appartementId = appartementId
                    )
                    navController.popBackStack()
                }
            }) {
                Text("Ajouter")
            }
        }
    }
}