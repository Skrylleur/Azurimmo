import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.model.Appartement
import kotlinx.coroutines.launch

// ViewModel pour gérer les données des appartements
class AppartementViewModel : ViewModel() {

    // Liste mutable des appartements
    private val _appartements = mutableStateOf(emptyList<Appartement>())
    val appartements: State<List<Appartement>> = _appartements

    init {
        // Charger les données initiales
        getAppartements()
    }

    // Fonction pour simuler le chargement des appartements
    private fun getAppartements() {
        viewModelScope.launch {
            _appartements.value = listOf(
                Appartement(
                    id = 1,
                    numero = 1,
                    surface = 71.00,
                    nbrePieces = 5,
                    description = "Appartement en bordure de ville"
                ),
                Appartement(
                    id = 2,
                    numero = 2,
                    surface = 45.00,
                    nbrePieces = 2,
                    description = "Appartement lumineux avec balcon"
                )
            )
        }
    }
}
