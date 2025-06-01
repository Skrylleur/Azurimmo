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
import bts.sio.azurimmo.viewmodel.LocataireViewModel
import bts.sio.azurimmo.viewmodel.ContratViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocataireScreen(
    navController: NavHostController,
    locataireViewModel: LocataireViewModel = viewModel(),
    contratViewModel: ContratViewModel = viewModel()
) {
    val locataires by locataireViewModel.locataires.collectAsState()
    val contrats by contratViewModel.contrats.collectAsState()

    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var dateN by remember { mutableStateOf("") }
    var lieuN by remember { mutableStateOf("") }
    var selectedContratId by remember { mutableStateOf<Long?>(null) }
    var expanded by remember { mutableStateOf(false) }

    var showForm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        locataireViewModel.loadAll()
        contratViewModel.loadContrats()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Locataires") },
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
                    Text("Ajouter un locataire")
                }
            }

            if (showForm) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nom,
                    onValueChange = { nom = it },
                    label = { Text("Nom") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = prenom,
                    onValueChange = { prenom = it },
                    label = { Text("Prénom") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dateN,
                    onValueChange = { dateN = it },
                    label = { Text("Date de naissance") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = lieuN,
                    onValueChange = { lieuN = it },
                    label = { Text("Lieu de naissance") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = contrats.find { contrat -> contrat.id == selectedContratId }?.let { selected ->
                            "Contrat n°${selected.id} - ${selected.dateEntree}"
                        } ?: "Sélectionner un contrat",
                        onValueChange = {},
                        label = { Text("Contrat") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
                            showForm = false
                            nom = ""
                            prenom = ""
                            dateN = ""
                            lieuN = ""
                            selectedContratId = null
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Annuler")
                    }

                    Button(
                        onClick = {
                            if (nom.isNotBlank() && prenom.isNotBlank() && dateN.isNotBlank() && lieuN.isNotBlank() && selectedContratId != null) {
                                locataireViewModel.addLocataire(nom, prenom, dateN, lieuN, selectedContratId!!)
                                nom = ""
                                prenom = ""
                                dateN = ""
                                lieuN = ""
                                selectedContratId = null
                                showForm = false
                            } else {
                                println("Erreur : données manquantes")
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
            Text("Liste des locataires", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(locataires) { loca ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("locataire/${loca.id}")
                            }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Nom : ${loca.nom}")
                            Text("Prénom : ${loca.prenom}")
                            Text("Date de naissance : ${loca.dateN}")
                            Text("Lieu de naissance : ${loca.lieuN}")
                        }
                    }
                }
            }
        }
    }
}