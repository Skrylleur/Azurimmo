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
import bts.sio.azurimmo.viewmodel.AppartementViewModel
import bts.sio.azurimmo.viewmodel.ContratViewModel
import bts.sio.azurimmo.viewmodel.InterventionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppartementDetailScreen(
    navController: NavHostController,
    appartementId: Long,
    appartementViewModel: AppartementViewModel = viewModel(),
    contratViewModel: ContratViewModel = viewModel(),
    interventionViewModel: InterventionViewModel = viewModel()
) {
    var showModifierDialog by remember { mutableStateOf(false) }
    var ongletActif by remember { mutableStateOf("contrats") }

    LaunchedEffect(appartementId) {
        appartementViewModel.loadAppartementById(appartementId)
        contratViewModel.loadParAppartement(appartementId)
        interventionViewModel.loadParAppartement(appartementId)
    }

    val appartement by appartementViewModel.appartementActuel.collectAsState()
    val contrats by contratViewModel.contrats.collectAsState()
    val interventions by interventionViewModel.interventions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail de l'appartement") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("appartements") {
                            popUpTo("appartements") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (appartement == null) {
                Text("Appartement introuvable")
                return@Column
            }

            Text("Numéro : ${appartement!!.numero}")
            Text("Surface : ${appartement!!.surface}")
            Text("Nombre de pièces : ${appartement!!.nbPieces}")
            Text("Description : ${appartement!!.description}")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showModifierDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Modifier l'appartement")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    appartement!!.id?.let { id ->
                        appartementViewModel.deleteAppartement(id)
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Supprimer", color = MaterialTheme.colorScheme.onError)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Onglets avec mise en évidence du bouton actif
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { ongletActif = "contrats" },
                    colors = if (ongletActif == "contrats")
                        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    else
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                ) {
                    Text("Contrats")
                }

                Button(
                    onClick = { ongletActif = "interventions" },
                    colors = if (ongletActif == "interventions")
                        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    else
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                ) {
                    Text("Interventions")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Affichage dynamique
            when (ongletActif) {
                "contrats" -> {
                    Text("Contrats associés :", style = MaterialTheme.typography.titleMedium)
                    if (contrats.isEmpty()) {
                        Text("Aucun contrat pour cet appartement.")
                    } else {
                        LazyColumn {
                            items(contrats) { contrat ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            navController.navigate("contrat/${contrat.id}")
                                        },
                                    shape = MaterialTheme.shapes.medium,
                                    tonalElevation = 2.dp,
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Entrée : ${contrat.dateEntree}")
                                        Text("Sortie : ${contrat.dateSortie}")
                                        Text("Loyer : ${contrat.montantLoyer} €")
                                        Text("Charges : ${contrat.montantCharges} €")
                                        Text("Statut : ${contrat.statut}")
                                    }
                                }
                            }
                        }
                    }
                }

                "interventions" -> {
                    Text("Interventions associées :", style = MaterialTheme.typography.titleMedium)
                    if (interventions.isEmpty()) {
                        Text("Aucune intervention pour cet appartement.")
                    } else {
                        LazyColumn {
                            items(interventions) { inter ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            navController.navigate("intervention/${inter.id}")
                                        },
                                    shape = MaterialTheme.shapes.medium,
                                    tonalElevation = 2.dp,
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Date : ${inter.dateInter}")
                                        Text("Type : ${inter.typeInter}")
                                        Text("Description : ${inter.description}")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // MODAL de modification
            if (showModifierDialog) {
                var numero by remember { mutableStateOf(appartement!!.numero.toString()) }
                var surface by remember { mutableStateOf(appartement!!.surface.toString()) }
                var nbPieces by remember { mutableStateOf(appartement!!.nbPieces.toString()) }
                var description by remember { mutableStateOf(appartement!!.description ?: "") }

                AlertDialog(
                    onDismissRequest = { showModifierDialog = false },
                    title = { Text("Modifier l'appartement") },
                    text = {
                        Column {
                            OutlinedTextField(value = numero, onValueChange = { numero = it }, label = { Text("Numéro") })
                            OutlinedTextField(value = surface, onValueChange = { surface = it }, label = { Text("Surface") })
                            OutlinedTextField(value = nbPieces, onValueChange = { nbPieces = it }, label = { Text("Nombre de pièces") })
                            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            val num = numero.toIntOrNull() ?: return@Button
                            val surf = surface.toFloatOrNull() ?: return@Button
                            val pieces = nbPieces.toIntOrNull() ?: return@Button
                            val batimentId = appartement!!.batiment?.id

                            if (batimentId != null) {
                                appartementViewModel.updateAppartement(
                                    id = appartementId,
                                    numero = num,
                                    surface = surf,
                                    nbPieces = pieces,
                                    description = description,
                                    batimentId = batimentId
                                )
                                showModifierDialog = false
                            }
                        }) {
                            Text("Valider")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { showModifierDialog = false }) {
                            Text("Annuler")
                        }
                    }
                )
            }
        }
    }
}