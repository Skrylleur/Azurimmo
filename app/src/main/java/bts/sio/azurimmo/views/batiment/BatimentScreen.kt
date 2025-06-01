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
import bts.sio.azurimmo.viewmodel.BatimentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatimentScreen(
    navController: NavHostController,
    viewModel: BatimentViewModel = viewModel(),
) {
    val batiments by viewModel.batiments.collectAsState()
    var adresse by remember { mutableStateOf("") }
    var ville by remember { mutableStateOf("") }
    var showForm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadBatiments()
        println("🔄 Rafraîchissement des bâtiments")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bâtiments") },
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
                    Text("Ajouter un bâtiment")
                }
            }

            if (showForm) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = adresse,
                    onValueChange = { adresse = it },
                    label = { Text("Adresse") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ville,
                    onValueChange = { ville = it },
                    label = { Text("Ville") },
                    modifier = Modifier.fillMaxWidth()
                )

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
                            if (adresse.isNotBlank() && ville.isNotBlank()) {
                                viewModel.addBatiment(adresse, ville)
                                adresse = ""
                                ville = ""
                                showForm = false
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

            Text("Liste des bâtiments", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(batiments) { batiment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable(enabled = batiment.id != null) {
                                batiment.id?.let { id ->
                                    println("🔎 Navigation vers batiment/$id") // ← DEBUG
                                    navController.navigate("batiment/$id")
                                }
                            }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("ID : ${batiment.id ?: "?"}")
                            Text("Adresse : ${batiment.adresse}")
                            Text("Ville : ${batiment.ville}")
                        }
                    }
                }
            }
        }
    }
}