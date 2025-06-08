package bts.sio.azurimmo.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bts.sio.azurimmo.viewmodel.InterventionViewModel
import bts.sio.azurimmo.model.Intervention
import bts.sio.azurimmo.viewmodel.AppartementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterventionScreen(
    navController: NavHostController,
    interventionViewModel: InterventionViewModel = viewModel(),
    appartementViewModel: AppartementViewModel = viewModel()
) {
    val interventions by interventionViewModel.interventions.collectAsState()
    val appartements by appartementViewModel.appartements.collectAsState()

    var description by remember { mutableStateOf("") }
    var typeInter by remember { mutableStateOf("") }
    var dateInter by remember { mutableStateOf("") }
    var selectedAppartementId by remember { mutableStateOf<Long?>(null) }
    var expanded by remember { mutableStateOf(false) }

    var interventionAModifier by remember { mutableStateOf<Intervention?>(null) }
    var modifierVisible by remember { mutableStateOf(false) }
    var showForm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        interventionViewModel.loadAll()
        appartementViewModel.loadAppartements()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Interventions") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (!showForm) {
                Button(
                    onClick = { showForm = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ajouter une intervention")
                }
            }

            if (showForm) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = typeInter, onValueChange = { typeInter = it }, label = { Text("Type") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = dateInter, onValueChange = { dateInter = it }, label = { Text("Date") }, modifier = Modifier.fillMaxWidth())

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = appartements.find { it.id == selectedAppartementId }?.let { "Appartement n°${it.id}" } ?: "Sélectionner un appartement",
                        onValueChange = {},
                        label = { Text("Appartement") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        appartements.forEach { appart ->
                            DropdownMenuItem(
                                text = { Text("Appartement n°${appart.id}") },
                                onClick = {
                                    selectedAppartementId = appart.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showForm = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Annuler")
                    }

                    Button(
                        onClick = {
                            if (
                                description.isNotBlank() &&
                                typeInter.isNotBlank() &&
                                dateInter.isNotBlank() &&
                                selectedAppartementId != null
                            ) {
                                interventionViewModel.addIntervention(
                                    description,
                                    typeInter,
                                    dateInter,
                                    selectedAppartementId!!
                                )
                                description = ""
                                typeInter = ""
                                dateInter = ""
                                selectedAppartementId = null
                                showForm = false
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Valider")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text("Liste des interventions", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(interventions) { inter ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("intervention/${inter.id}")
                            }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Description : ${inter.description}")
                            Text("Type : ${inter.typeInter}")
                            Text("Date : ${inter.dateInter}")
                        }
                    }
                }
            }
        }
    }
}