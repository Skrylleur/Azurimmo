import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import bts.sio.azurimmo.model.Batiment
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
// ViewModel pour gérer les données des bâtiments
class BatimentViewModel : ViewModel() {
    // Liste mutable des bâtiments
    private val _batiments = mutableStateOf(emptyList<Batiment>())
    val batiments: State<List<Batiment>> = _batiments
    init {
// Simuler un chargement de données initiales
        getBatiments()
    }
    // Fonction pour simuler le chargement de bâtiments
    private fun getBatiments() {
        viewModelScope.launch {
            _batiments.value = listOf(
                Batiment(1, "123 Rue Principale", "Nice"),
                Batiment(2, "456 Avenue des Champs", "Marseille"),
                Batiment(3, "789 Boulevard Haussmann", "Marseille")
            )
        }
    }
}