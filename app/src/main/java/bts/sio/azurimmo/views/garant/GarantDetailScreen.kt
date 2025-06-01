package bts.sio.azurimmo.views.garant

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
import bts.sio.azurimmo.viewmodel.GarantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarantDetailScreen(
    navController: NavHostController,
    garantId: Long,
    viewModel: GarantViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadAll()
    }
    val garant = viewModel.garants.collectAsState().value.find { it.id == garantId }

    var showModifierDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail du garant") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (garant == null) {
            Text("Garant introuvable", modifier = Modifier.padding(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Nom : ") }
                    append(garant.nom.toString())
                })

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Prénom : ") }
                    append(garant.prenom.toString())
                })

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showModifierDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier le garant")
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { showDeleteConfirm = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Supprimer le garant")
                }

                if (showModifierDialog) {
                    var nouveauNom by remember { mutableStateOf(garant.nom.toString()) }
                    var nouveauPrenom by remember { mutableStateOf(garant.prenom.toString()) }

                    AlertDialog(
                        onDismissRequest = { showModifierDialog = false },
                        title = { Text("Modifier le garant") },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = nouveauNom,
                                    onValueChange = { nouveauNom = it },
                                    label = { Text("Nouveau nom") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = nouveauPrenom,
                                    onValueChange = { nouveauPrenom = it },
                                    label = { Text("Nouveau prénom") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                garant.id?.let {
                                    viewModel.updateGarant(
                                        it,
                                        nouveauNom,
                                        nouveauPrenom,
                                        garant.contrat.id
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
                        text = { Text("Voulez-vous vraiment supprimer ce garant ?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    garant.id?.let { viewModel.deleteGarant(it) }
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