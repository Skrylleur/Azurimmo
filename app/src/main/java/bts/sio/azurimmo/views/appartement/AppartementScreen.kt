package bts.sio.azurimmo.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bts.sio.azurimmo.viewmodel.AppartementViewModel
import bts.sio.azurimmo.model.Appartement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppartementScreen(
    navController: NavHostController,
    appartementViewModel: AppartementViewModel = viewModel()
) {
    val appartements by appartementViewModel.appartements.collectAsState()

    var numero by remember { mutableStateOf("") }
    var surface by remember { mutableStateOf("") }
    var nb_pieces by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var appartementAModifier by remember { mutableStateOf<Appartement?>(null) }
    var modifierVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Liste des appartements") },
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
            Text("Ajouter un appartement", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(value = numero, onValueChange = { numero = it }, label = { Text("Numéro") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = surface, onValueChange = { surface = it }, label = { Text("Surface") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = nb_pieces, onValueChange = { nb_pieces = it }, label = { Text("Nombre de pièces") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())

            Button(
                onClick = {
                    if (
                        numero.isNotBlank() && surface.isNotBlank() &&
                        nb_pieces.isNotBlank() && description.isNotBlank()
                    ) {
                        appartementViewModel.addAppartement(numero, surface, nb_pieces, description)
                        numero = ""
                        surface = ""
                        nb_pieces = ""
                        description = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Ajouter")
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text("Liste des appartements", style = MaterialTheme.typography.headlineSmall)

            LazyColumn {
                items(appartements) { appartement ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("ID : ${appartement.id}")
                                Text("Numéro : ${appartement.numero}")
                                Text("Surface : ${appartement.surface}")
                                Text("Nombre de pièces : ${appartement.nb_pieces}")
                                Text("Description : ${appartement.description}")
                            }
                            Row {
                                IconButton(onClick = {
                                    appartementAModifier = appartement
                                    modifierVisible = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Modifier")
                                }
                                IconButton(onClick = {
                                    appartementViewModel.deleteBatiment(appartement.id)
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Supprimer")
                                }
                            }
                        }
                    }
                }
            }

            if (modifierVisible && appartementAModifier != null) {
                var nouveauNumero by remember { mutableStateOf(appartementAModifier!!.numero) }
                var nouvelleSurface by remember { mutableStateOf(appartementAModifier!!.surface) }
                var nouveauNbPieces by remember { mutableStateOf(appartementAModifier!!.nb_pieces) }
                var nouvelleDescription by remember { mutableStateOf(appartementAModifier!!.description) }

                AlertDialog(
                    onDismissRequest = {
                        modifierVisible = false
                        appartementAModifier = null
                    },
                    title = { Text("Modifier l'appartement") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = nouveauNumero,
                                onValueChange = { nouveauNumero = it },
                                label = { Text("Nouveau numéro") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = nouvelleSurface,
                                onValueChange = { nouvelleSurface = it },
                                label = { Text("Nouvelle surface") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = nouveauNbPieces,
                                onValueChange = { nouveauNbPieces = it },
                                label = { Text("Nouveau nombre de pièces") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = nouvelleDescription,
                                onValueChange = { nouvelleDescription = it },
                                label = { Text("Nouvelle description") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            appartementViewModel.updateAppartement(
                                appartementAModifier!!.id,
                                nouveauNumero,
                                nouvelleSurface,
                                nouveauNbPieces,
                                nouvelleDescription
                            )
                            modifierVisible = false
                            appartementAModifier = null
                        }) {
                            Text("Valider")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = {
                            modifierVisible = false
                            appartementAModifier = null
                        }) {
                            Text("Annuler")
                        }
                    }
                )
            }
        }
    }
}