package bts.sio.azurimmo.views.appartement

import AppartementViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import bts.sio.azurimmo.model.Appartement


// Fonction Composable pour afficher la liste des bâtiments
@Composable
fun AppartementList() {
// Récupérer le ViewModel dans le composable avec viewModel()
    val viewModel: AppartementViewModel = viewModel()
// Observer les données des bâtiments via le ViewModel
    val appartements = viewModel.appartements.value
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(appartements) { appartement ->
            AppartementCard(appartement = appartement)
        }
    }
}


fun items(appartements: List<Appartement>, any: @Composable Any) {
    TODO("Not yet implemented")
}