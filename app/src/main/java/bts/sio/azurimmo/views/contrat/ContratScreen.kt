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
import bts.sio.azurimmo.model.Appartement
import bts.sio.azurimmo.viewmodel.ContratViewModel
import bts.sio.azurimmo.viewmodel.AppartementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContratScreen(
    navController: NavHostController,
    contratViewModel: ContratViewModel = viewModel(),
    appartementViewModel: AppartementViewModel = viewModel()
) {
    val contrats by contratViewModel.contrats.collectAsState()
    val appartements by appartementViewModel.appartements.collectAsState()

    var dateEntree by remember { mutableStateOf("") }
    var dateSortie by remember { mutableStateOf("") }
    var montantLoyer by remember { mutableStateOf("") }
    var montantCharges by remember { mutableStateOf("") }
    var statut by remember { mutableStateOf("") }
    var selectedAppartement by remember { mutableStateOf<Appartement?>(null) }
    var showForm by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    // 🔄 Chargement des données à l'affichage
    LaunchedEffect(Unit) {
        appartementViewModel.loadAppartements()
        contratViewModel.loadContrats()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contrats") },
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
                    Text("Ajouter un contrat")
                }
            }

            if (showForm) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(value = dateEntree, onValueChange = { dateEntree = it }, label = { Text("Date d'entrée") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = dateSortie, onValueChange = { dateSortie = it }, label = { Text("Date de sortie") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = montantLoyer, onValueChange = { montantLoyer = it }, label = { Text("Montant loyer") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = montantCharges, onValueChange = { montantCharges = it }, label = { Text("Montant charges") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = statut, onValueChange = { statut = it }, label = { Text("Statut") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedAppartement?.description ?: "Sélectionner un appartement",
                        onValueChange = {},
                        label = { Text("Appartement") },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        appartements.forEach { appart ->
                            DropdownMenuItem(
                                text = { Text("${appart.description} (${appart.numero})") },
                                onClick = {
                                    selectedAppartement = appart
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
                        onClick = {
                            showForm = false
                            dateEntree = ""
                            dateSortie = ""
                            montantLoyer = ""
                            montantCharges = ""
                            statut = ""
                            selectedAppartement = null
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Annuler")
                    }

                    Button(
                        onClick = {
                            if (
                                dateEntree.isNotBlank() && dateSortie.isNotBlank()
                                && montantLoyer.isNotBlank() && montantCharges.isNotBlank()
                                && statut.isNotBlank() && selectedAppartement != null
                            ) {
                                contratViewModel.addContrat(
                                    dateEntree,
                                    dateSortie,
                                    montantLoyer,
                                    montantCharges,
                                    statut,
                                    selectedAppartement!!.id!!
                                )
                                showForm = false
                                dateEntree = ""
                                dateSortie = ""
                                montantLoyer = ""
                                montantCharges = ""
                                statut = ""
                                selectedAppartement = null
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Valider l’ajout")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text("Liste des contrats", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(contrats) { contrat ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("contrat/${contrat.id}")
                            }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("ID : ${contrat.id}")
                            Text("Date d'entrée : ${contrat.dateEntree}")
                            Text("Date de sortie : ${contrat.dateSortie}")
                            Text("Montant loyer : ${contrat.montantLoyer}")
                            Text("Montant charges : ${contrat.montantCharges}")
                            Text("Statut : ${contrat.statut}")
                            Text("Appartement ID : ${contrat.appartement.id}")
                        }
                    }
                }
            }
        }
    }
}