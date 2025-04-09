package bts.sio.azurimmo.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bts.sio.azurimmo.viewmodel.BatimentViewModel
import bts.sio.azurimmo.model.Batiment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatimentScreen(
    navController: NavHostController,
    viewModel: BatimentViewModel = viewModel()
) {
    val batiments by viewModel.batiments.collectAsState()
    var adresse by remember { mutableStateOf("") }
    var ville by remember { mutableStateOf("") }
    var batimentAModifier by remember { mutableStateOf<Batiment?>(null) }
    var modifierVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Liste des bâtiments") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour menu")
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

            Text("Ajouter un bâtiment", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = adresse,
                onValueChange = { adresse = it },
                label = { Text("Adresse") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ville,
                onValueChange = { ville = it },
                label = { Text("Ville") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (adresse.isNotBlank() && ville.isNotBlank()) {
                        viewModel.addBatiment(adresse, ville)
                        adresse = ""
                        ville = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Ajouter")
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text("Liste des bâtiments", style = MaterialTheme.typography.headlineSmall)

            LazyColumn {
                items(batiments) { batiment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("ID : ${batiment.id}")
                                Text("Adresse : ${batiment.adresse}")
                                Text("Ville : ${batiment.ville}")
                            }
                            Row {
                                IconButton(onClick = {
                                    batimentAModifier = batiment
                                    modifierVisible = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Modifier")
                                }

                                IconButton(onClick = {
                                    viewModel.deleteBatiment(batiment.id)
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Supprimer")
                                }
                            }
                        }
                    }
                }
            }

            if (modifierVisible && batimentAModifier != null) {
                var nouvelleAdresse by remember { mutableStateOf(batimentAModifier!!.adresse) }
                var nouvelleVille by remember { mutableStateOf(batimentAModifier!!.ville) }

                AlertDialog(
                    onDismissRequest = {
                        modifierVisible = false
                        batimentAModifier = null
                    },
                    title = { Text("Modifier le bâtiment") },
                    text = {
                        Column {
                            Text("Adresse actuelle : ${batimentAModifier!!.adresse}")
                            Text("Ville actuelle : ${batimentAModifier!!.ville}")
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = nouvelleAdresse,
                                onValueChange = { nouvelleAdresse = it },
                                label = { Text("Nouvelle adresse") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = nouvelleVille,
                                onValueChange = { nouvelleVille = it },
                                label = { Text("Nouvelle ville") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.updateBatiment(
                                batimentAModifier!!.id,
                                nouvelleAdresse,
                                nouvelleVille
                            )
                            modifierVisible = false
                            batimentAModifier = null
                        }) {
                            Text("Valider")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = {
                            modifierVisible = false
                            batimentAModifier = null
                        }) {
                            Text("Annuler")
                        }
                    }
                )
            }
        }
    }
}