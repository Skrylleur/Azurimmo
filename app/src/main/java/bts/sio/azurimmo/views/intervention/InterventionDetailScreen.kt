package bts.sio.azurimmo.views.intervention

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
import bts.sio.azurimmo.viewmodel.InterventionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterventionDetailScreen(
    navController: NavHostController,
    interventionId: Long,
    viewModel: InterventionViewModel = viewModel()
) {
    val intervention = viewModel.interventions.collectAsState().value.find { it.id == interventionId }

    var showModifierDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadAll()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail de l'intervention") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (intervention == null) {
            Text("Intervention introuvable", modifier = Modifier.padding(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Description : ") }
                    append(intervention.description.toString())
                })

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Type d'intervention : ") }
                    append(intervention.typeInter.toString())
                })

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Date d'intervention : ") }
                    append(intervention.dateInter.toString())
                })

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showModifierDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier l'intervention'")
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { showDeleteConfirm = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Supprimer l'intervention")
                }

                if (showModifierDialog) {
                    var nouvelleDescription by remember { mutableStateOf(intervention.description.toString()) }
                    var nouveauTypeInter by remember { mutableStateOf(intervention.typeInter.toString()) }
                    var nouvelleDateInter by remember { mutableStateOf(intervention.dateInter.toString()) }

                    AlertDialog(
                        onDismissRequest = { showModifierDialog = false },
                        title = { Text("Modifier l'intervention") },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = nouvelleDescription,
                                    onValueChange = { nouvelleDescription = it },
                                    label = { Text("Nouvelle description") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = nouveauTypeInter,
                                    onValueChange = { nouveauTypeInter = it },
                                    label = { Text("Nouveau type d'intervention") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = nouvelleDateInter,
                                    onValueChange = { nouvelleDateInter = it },
                                    label = { Text("Nouvelle date d'intervention") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                intervention.id?.let {
                                    viewModel.updateIntervention(
                                        it,
                                        nouvelleDescription,
                                        nouveauTypeInter,
                                        nouvelleDateInter,
                                        intervention.appartement.id
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
                        text = { Text("Voulez-vous vraiment supprimer cette intervention ?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    intervention.id?.let {
                                        viewModel.deleteIntervention(it, intervention.appartement.id)
                                    }
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