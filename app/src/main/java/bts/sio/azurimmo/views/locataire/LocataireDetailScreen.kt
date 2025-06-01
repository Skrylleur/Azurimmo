package bts.sio.azurimmo.views.locataire

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
import bts.sio.azurimmo.viewmodel.LocataireViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocataireDetailScreen(
    navController: NavHostController,
    locataireId: Long,
    viewModel: LocataireViewModel = viewModel()
) {
    val locataire = viewModel.locataires.collectAsState().value.find { it.id == locataireId }

    var showModifierDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadAll()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail du locataire") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (locataire == null) {
            Text("Locataire introuvable", modifier = Modifier.padding(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Nom : ") }
                    append(locataire.nom.toString())
                })

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Prénom : ") }
                    append(locataire.prenom.toString())
                })

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Date de naissance : ") }
                    append(locataire.dateN.toString())
                })

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Lieu de naissance : ") }
                    append(locataire.lieuN.toString())
                })

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showModifierDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier le locataire")
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { showDeleteConfirm = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Supprimer le locataire")
                }

                if (showModifierDialog) {
                    var nouveauNom by remember { mutableStateOf(locataire.nom.toString()) }
                    var nouveauPrenom by remember { mutableStateOf(locataire.prenom.toString()) }
                    var nouvelleDateN by remember { mutableStateOf(locataire.dateN.toString()) }
                    var nouveauLieuN by remember { mutableStateOf(locataire.lieuN.toString()) }

                    AlertDialog(
                        onDismissRequest = { showModifierDialog = false },
                        title = { Text("Modifier le locataire") },
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
                                OutlinedTextField(
                                    value = nouvelleDateN,
                                    onValueChange = { nouvelleDateN = it },
                                    label = { Text("Nouvelle date de naissance") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = nouveauLieuN,
                                    onValueChange = { nouveauLieuN = it },
                                    label = { Text("Nouveau lieu de naissance") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                locataire.id?.let {
                                    viewModel.updateLocataire(
                                        it,
                                        nouveauNom,
                                        nouveauPrenom,
                                        nouvelleDateN,
                                        nouveauLieuN,
                                        locataire.contrat.id
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
                        text = { Text("Voulez-vous vraiment supprimer ce locataire ?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    locataire.id?.let { viewModel.deleteLocataire(it) }
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