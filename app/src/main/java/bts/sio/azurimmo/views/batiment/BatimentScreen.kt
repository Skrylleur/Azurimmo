package bts.sio.azurimmo.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.viewmodel.BatimentViewModel
import bts.sio.azurimmo.model.Batiment

@Composable
fun BatimentScreen(viewModel: BatimentViewModel = viewModel()) {
    val batiments by viewModel.batiments.collectAsState()

    var adresse by remember { mutableStateOf("") }
    var ville by remember { mutableStateOf("") }

    var batimentAModifier by remember { mutableStateOf<Batiment?>(null) }
    var modifierVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

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

        // 🔧 Affichage de la fenêtre de modification en dehors du LazyColumn
        if (modifierVisible && batimentAModifier != null) {
            var nouvelleAdresse by remember { mutableStateOf(batimentAModifier!!.adresse) }
            var nouvelleVille by remember { mutableStateOf(batimentAModifier!!.ville) }

            AlertDialog(
                onDismissRequest = {
                    modifierVisible = false
                    batimentAModifier = null
                },
                title = {
                    Text("Modifier le bâtiment")
                },
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