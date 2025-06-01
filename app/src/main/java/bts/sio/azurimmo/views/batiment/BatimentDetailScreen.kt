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
import bts.sio.azurimmo.viewmodel.BatimentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatimentDetailScreen(
    navController: NavHostController,
    batimentId: Long,
    batimentViewModel: BatimentViewModel = viewModel(),
    appartementViewModel: AppartementViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    // 👉 Chargement dynamique des appartements liés
    LaunchedEffect(batimentId) {
        appartementViewModel.loadParBatiment(batimentId)
    }

    val batiment = batimentViewModel.batiments.collectAsState().value.find { it.id == batimentId }
    val appartements = appartementViewModel.appartements.collectAsState().value

    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails du bâtiment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { padding ->
        batiment?.let { bat ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text("Adresse : ${bat.adresse}", style = MaterialTheme.typography.titleMedium)
                Text("Ville : ${bat.ville}")

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showEditDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier le bâtiment")
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Supprimer le bâtiment")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Text("Appartements associés", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                if (appartements.isEmpty()) {
                    Text("Aucun appartement trouvé.", style = MaterialTheme.typography.bodyMedium)
                } else {
                    LazyColumn {
                        items(appartements) { appart ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable {
                                        navController.navigate("appartement/${appart.id}")
                                    },
                                shape = MaterialTheme.shapes.medium,
                                tonalElevation = 2.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant // ou une teinte personnalisée
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Numéro : ${appart.numero}", style = MaterialTheme.typography.titleMedium)
                                    Text("Surface : ${appart.surface} m²", style = MaterialTheme.typography.bodyMedium)
                                    Text("Nombre de pièces : ${appart.nbPieces}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Description : ${appart.description}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        navController.navigate("ajouterAppartement/$batimentId")
                    }) {
                        Text("Ajouter un appartement")
                    }
                }
            }
        }

        // 🔴 MODAL SUPPRESSION
        if (showDeleteDialog && batiment != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Supprimer le bâtiment") },
                text = { Text("Êtes-vous sûr de vouloir supprimer ce bâtiment ?") },
                confirmButton = {
                    Button(
                        onClick = {
                            scope.launch {
                                batimentViewModel.deleteBatiment(batimentId)
                                navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Supprimer")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showDeleteDialog = false }) {
                        Text("Annuler")
                    }
                }
            )
        }

        // 🔵 MODAL MODIFICATION
        if (showEditDialog && batiment != null) {
            var nouvelleAdresse by remember { mutableStateOf(batiment.adresse) }
            var nouvelleVille by remember { mutableStateOf(batiment.ville) }

            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Modifier le bâtiment") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = nouvelleAdresse,
                            onValueChange = { nouvelleAdresse = it },
                            label = { Text("Adresse") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = nouvelleVille,
                            onValueChange = { nouvelleVille = it },
                            label = { Text("Ville") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        batimentViewModel.updateBatiment(batiment.id!!, nouvelleAdresse, nouvelleVille)
                        showEditDialog = false
                    }) {
                        Text("Valider")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showEditDialog = false }) {
                        Text("Annuler")
                    }
                }
            )
        }
    }
}