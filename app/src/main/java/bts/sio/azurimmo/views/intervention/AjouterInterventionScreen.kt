package bts.sio.azurimmo.views.intervention

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bts.sio.azurimmo.viewmodel.InterventionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjouterInterventionScreen(
    appartementId: Long,
    navController: NavHostController,
    interventionViewModel: InterventionViewModel = viewModel()
) {
    var description by remember { mutableStateOf("") }
    var typeInter by remember { mutableStateOf("") }
    var dateInter by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ajouter une intervention") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
            OutlinedTextField(value = typeInter, onValueChange = { typeInter = it }, label = { Text("Type d'intervention") })
            OutlinedTextField(value = dateInter, onValueChange = { dateInter = it }, label = { Text("Date d'intervention (YYYY-MM-DD)") })

            Button(onClick = {
                if (description.isNotBlank() && typeInter.isNotBlank() && dateInter.isNotBlank()) {
                    interventionViewModel.addIntervention(
                        description = description,
                        typeInter = typeInter,
                        dateInter = dateInter,
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