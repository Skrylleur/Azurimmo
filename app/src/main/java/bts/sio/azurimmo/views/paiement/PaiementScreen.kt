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
import bts.sio.azurimmo.viewmodel.PaiementViewModel
import bts.sio.azurimmo.viewmodel.ContratViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaiementScreen(
    navController: NavHostController,
    paiementViewModel: PaiementViewModel = viewModel(),
    contratViewModel: ContratViewModel = viewModel()
) {
    val paiements by paiementViewModel.paiements.collectAsState()
    val contrats by contratViewModel.contrats.collectAsState()

    var montant by remember { mutableStateOf("") }
    var datePaiement by remember { mutableStateOf("") }
    var selectedContratId by remember { mutableStateOf<Long?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var showForm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        contratViewModel.loadContrats()
        paiementViewModel.loadAll()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paiements") },
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
                Button(onClick = { showForm = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("Ajouter un paiement")
                }
            }

            if (showForm) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = montant,
                    onValueChange = { montant = it },
                    label = { Text("Montant") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = datePaiement,
                    onValueChange = { datePaiement = it },
                    label = { Text("Date de paiement (yyyy-MM-dd)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = contrats.find { it.id == selectedContratId }?.let {
                            "Contrat n°${it.id} - ${it.dateEntree}"
                        } ?: "Sélectionner un contrat",
                        onValueChange = {},
                        label = { Text("Contrat") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        contrats.forEach { contrat ->
                            DropdownMenuItem(
                                text = { Text("Contrat n°${contrat.id} - ${contrat.dateEntree}") },
                                onClick = {
                                    selectedContratId = contrat.id
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
                            montant = ""
                            datePaiement = ""
                            selectedContratId = null
                            showForm = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Annuler")
                    }

                    Button(
                        onClick = {
                            val montantDouble = montant.toDoubleOrNull()
                            if (montantDouble != null && datePaiement.isNotBlank() && selectedContratId != null) {
                                paiementViewModel.addPaiement(montantDouble, datePaiement, selectedContratId!!)
                                montant = ""
                                datePaiement = ""
                                selectedContratId = null
                                showForm = false
                            } else {
                                println("❌ Données manquantes ou invalides")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Valider")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text("Liste des paiements", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(paiements) { paiement ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("paiement/${paiement.id}")
                            }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Montant : ${paiement.montant}")
                            Text("Date : ${paiement.datePaiement}")
                        }
                    }
                }
            }
        }
    }
}