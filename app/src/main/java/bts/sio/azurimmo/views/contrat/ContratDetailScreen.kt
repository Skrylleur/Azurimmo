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
import bts.sio.azurimmo.viewmodel.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContratDetailScreen(
    navController: NavHostController,
    contratId: Long,
    contratViewModel: ContratViewModel = viewModel(),
    paiementViewModel: PaiementViewModel = viewModel(),
    locataireViewModel: LocataireViewModel = viewModel(),
    garantViewModel: GarantViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val contrat = contratViewModel.contrats.collectAsState().value.find { it.id == contratId }

    var showModifierDialog by remember { mutableStateOf(false) }
    var ongletActif by remember { mutableStateOf("paiements") }

    // Chargement des entités liées
    LaunchedEffect(contratId) {
        paiementViewModel.loadParContrat(contratId)
        locataireViewModel.loadParContrat(contratId)
        garantViewModel.loadParContrat(contratId)
    }

    val paiements = paiementViewModel.paiements.collectAsState().value
    val locataires = locataireViewModel.locataires.collectAsState().value
    val garants = garantViewModel.garants.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail du contrat") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("contrats") {
                            popUpTo("contrats") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { padding ->
        if (contrat == null) {
            Text("Contrat introuvable", modifier = Modifier.padding(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // Détails
                Text("Date d’entrée : ${contrat.dateEntree}")
                Text("Date de sortie : ${contrat.dateSortie}")
                Text("Loyer : ${contrat.montantLoyer}")
                Text("Charges : ${contrat.montantCharges}")
                Text("Statut : ${contrat.statut}")

                Spacer(modifier = Modifier.height(16.dp))

                // Boutons modifier / supprimer
                Button(
                    onClick = { showModifierDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier le contrat")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        contrat.id?.let { id ->
                            contratViewModel.deleteContrat(id)
                            contratViewModel.loadContrats()
                            navController.navigate("contrats") {
                                popUpTo("contrats") { inclusive = true }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Supprimer ce contrat", color = MaterialTheme.colorScheme.onError)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Onglets
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { ongletActif = "paiements" },
                        colors = if (ongletActif == "paiements")
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        else
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    ) {
                        Text("Paiements")
                    }

                    Button(
                        onClick = { ongletActif = "locataires" },
                        colors = if (ongletActif == "locataires")
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        else
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    ) {
                        Text("Locataires")
                    }

                    Button(
                        onClick = { ongletActif = "garants" },
                        colors = if (ongletActif == "garants")
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        else
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    ) {
                        Text("Garants")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Contenu dynamique
                when (ongletActif) {
                    "paiements" -> {
                        Text("Paiements liés :", style = MaterialTheme.typography.titleMedium)
                        LazyColumn {
                            items(paiements) { paiement ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { navController.navigate("paiement/${paiement.id}") },
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Montant : ${paiement.montant}")
                                        Text("Date : ${paiement.datePaiement}")
                                    }
                                }
                            }
                        }
                    }

                    "locataires" -> {
                        Text("Locataires liés :", style = MaterialTheme.typography.titleMedium)
                        LazyColumn {
                            items(locataires) { locataire ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { navController.navigate("locataire/${locataire.id}") },
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Nom : ${locataire.nom}")
                                        Text("Prénom : ${locataire.prenom}")
                                    }
                                }
                            }
                        }
                    }

                    "garants" -> {
                        Text("Garants liés :", style = MaterialTheme.typography.titleMedium)
                        LazyColumn {
                            items(garants) { garant ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { navController.navigate("garant/${garant.id}") },
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Nom : ${garant.nom}")
                                        Text("Prénom : ${garant.prenom}")
                                    }
                                }
                            }
                        }
                    }
                }

                // Modal modification
                if (showModifierDialog) {
                    var dateEntree by remember { mutableStateOf(contrat.dateEntree) }
                    var dateSortie by remember { mutableStateOf(contrat.dateSortie) }
                    var montantLoyer by remember { mutableStateOf(contrat.montantLoyer) }
                    var montantCharges by remember { mutableStateOf(contrat.montantCharges) }
                    var statut by remember { mutableStateOf(contrat.statut) }

                    AlertDialog(
                        onDismissRequest = { showModifierDialog = false },
                        title = { Text("Modifier le contrat") },
                        text = {
                            Column {
                                OutlinedTextField(value = dateEntree, onValueChange = { dateEntree = it }, label = { Text("Date d’entrée") })
                                OutlinedTextField(value = dateSortie, onValueChange = { dateSortie = it }, label = { Text("Date de sortie") })
                                OutlinedTextField(value = montantLoyer, onValueChange = { montantLoyer = it }, label = { Text("Loyer") })
                                OutlinedTextField(value = montantCharges, onValueChange = { montantCharges = it }, label = { Text("Charges") })
                                OutlinedTextField(value = statut, onValueChange = { statut = it }, label = { Text("Statut") })
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                contratViewModel.updateContrat(
                                    id = contratId,
                                    dateEntree = dateEntree,
                                    dateSortie = dateSortie,
                                    montantLoyer = montantLoyer,
                                    montantCharges = montantCharges,
                                    statut = statut,
                                    appartementId = contrat.appartement.id
                                )
                                showModifierDialog = false
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
}