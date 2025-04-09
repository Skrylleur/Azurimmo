package bts.sio.azurimmo.views

import Contrat
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
import bts.sio.azurimmo.viewmodel.ContratViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContratScreen(
    navController: NavHostController,
    contratViewModel: ContratViewModel = viewModel()
) {
    val contrats by contratViewModel.contrats.collectAsState()

    var dateEntree by remember { mutableStateOf("") }
    var dateSortie by remember { mutableStateOf("") }
    var montantLoyer by remember { mutableStateOf("") }
    var montantCharges by remember { mutableStateOf("") }
    var statut by remember { mutableStateOf("") }

    var contratAModifier by remember { mutableStateOf<Contrat?>(null) }
    var modifierVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Liste des contrats") },
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
            Text("Ajouter un contrat", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(value = dateEntree, onValueChange = { dateEntree = it }, label = { Text("Date d'entrée") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = dateSortie, onValueChange = { dateSortie = it }, label = { Text("Date de sortie") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = montantLoyer, onValueChange = { montantLoyer = it }, label = { Text("Montant loyer") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = montantCharges, onValueChange = { montantCharges = it }, label = { Text("Montant charges") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = statut, onValueChange = { statut = it }, label = { Text("Statut") }, modifier = Modifier.fillMaxWidth())

            Button(
                onClick = {
                    if (
                        dateEntree.isNotBlank() &&
                        dateSortie.isNotBlank() &&
                        montantLoyer.isNotBlank() &&
                        montantCharges.isNotBlank() &&
                        statut.isNotBlank()
                    ) {
                        contratViewModel.addContrat(
                            dateEntree, dateSortie, montantLoyer, montantCharges, statut
                        )
                        dateEntree = ""
                        dateSortie = ""
                        montantLoyer = ""
                        montantCharges = ""
                        statut = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Ajouter")
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text("Liste des contrats", style = MaterialTheme.typography.headlineSmall)

            LazyColumn {
                items(contrats) { contrat ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("ID : ${contrat.id}")
                                Text("Date d'entrée : ${contrat.dateEntree}")
                                Text("Date de sortie : ${contrat.dateSortie}")
                                Text("Montant loyer : ${contrat.montantLoyer}")
                                Text("Montant charges : ${contrat.montantCharges}")
                                Text("Statut : ${contrat.statut}")
                            }
                            Row {
                                IconButton(onClick = {
                                    contratAModifier = contrat
                                    modifierVisible = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Modifier")
                                }
                                IconButton(onClick = {
                                    contratViewModel.deleteContrat(contrat.id)
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Supprimer")
                                }
                            }
                        }
                    }
                }
            }

            if (modifierVisible && contratAModifier != null) {
                var nouvelleDateEntree by remember { mutableStateOf(contratAModifier!!.dateEntree) }
                var nouvelleDateSortie by remember { mutableStateOf(contratAModifier!!.dateSortie) }
                var nouveauMontantLoyer by remember { mutableStateOf(contratAModifier!!.montantLoyer.toString()) }
                var nouveauMontantCharges by remember { mutableStateOf(contratAModifier!!.montantCharges.toString()) }
                var nouveauStatut by remember { mutableStateOf(contratAModifier!!.statut) }

                AlertDialog(
                    onDismissRequest = {
                        modifierVisible = false
                        contratAModifier = null
                    },
                    title = { Text("Modifier le contrat") },
                    text = {
                        Column {
                            OutlinedTextField(value = nouvelleDateEntree, onValueChange = { nouvelleDateEntree = it }, label = { Text("Nouvelle date d'entrée") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = nouvelleDateSortie, onValueChange = { nouvelleDateSortie = it }, label = { Text("Nouvelle date sortie") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = nouveauMontantLoyer, onValueChange = { nouveauMontantLoyer = it }, label = { Text("Nouveau montant loyer") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = nouveauMontantCharges, onValueChange = { nouveauMontantCharges = it }, label = { Text("Nouveau montant charges") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = nouveauStatut, onValueChange = { nouveauStatut = it }, label = { Text("Nouveau statut") }, modifier = Modifier.fillMaxWidth())
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            contratViewModel.updateContrat(
                                contratAModifier!!.id,
                                nouvelleDateEntree,
                                nouvelleDateSortie,
                                nouveauMontantLoyer,
                                nouveauMontantCharges,
                                nouveauStatut
                            )
                            modifierVisible = false
                            contratAModifier = null
                        }) {
                            Text("Valider")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = {
                            modifierVisible = false
                            contratAModifier = null
                        }) {
                            Text("Annuler")
                        }
                    }
                )
            }
        }
    }
}