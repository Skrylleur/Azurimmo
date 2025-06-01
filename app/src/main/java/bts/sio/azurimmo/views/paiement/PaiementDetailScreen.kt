package bts.sio.azurimmo.views.paiement

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bts.sio.azurimmo.viewmodel.PaiementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaiementDetailScreen(
    navController: NavHostController,
    paiementId: Long,
    viewModel: PaiementViewModel = viewModel()
) {
    val paiement = viewModel.paiements.collectAsState().value.find { it.id == paiementId }

    var showModifierDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadAll()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail du paiement") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (paiement == null) {
            Text("Paiement introuvable", modifier = Modifier.padding(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Montant : ") }
                    append(paiement.montant.toString())
                })

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Date de paiement : ") }
                    append(paiement.datePaiement)
                })

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showModifierDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier le paiement")
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { showDeleteConfirm = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Supprimer le paiement")
                }

                if (showModifierDialog) {
                    var nouveauMontant by remember { mutableStateOf(paiement.montant.toString()) }
                    var nouvelleDatePaiement by remember { mutableStateOf(paiement.datePaiement) }

                    AlertDialog(
                        onDismissRequest = { showModifierDialog = false },
                        title = { Text("Modifier le paiement") },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = nouveauMontant,
                                    onValueChange = { nouveauMontant = it },
                                    label = { Text("Nouveau montant") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = nouvelleDatePaiement,
                                    onValueChange = { nouvelleDatePaiement = it },
                                    label = { Text("Nouvelle date de paiement") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                paiement.id?.let {
                                    viewModel.updatePaiement(
                                        it,
                                        nouveauMontant.toDoubleOrNull() ?: 0.0,
                                        nouvelleDatePaiement,
                                        paiement.contrat.id
                                    )
                                }
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

                if (showDeleteConfirm) {
                    AlertDialog(
                        onDismissRequest = { showDeleteConfirm = false },
                        title = { Text("Confirmer la suppression") },
                        text = { Text("Voulez-vous vraiment supprimer ce paiement ?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    paiement.id?.let { viewModel.deletePaiement(it) }
                                    showDeleteConfirm = false
                                    navController.popBackStack()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Supprimer")
                            }
                        },
                        dismissButton = {
                            OutlinedButton(onClick = { showDeleteConfirm = false }) {
                                Text("Annuler")
                            }
                        }
                    )
                }
            }
        }
    }
}