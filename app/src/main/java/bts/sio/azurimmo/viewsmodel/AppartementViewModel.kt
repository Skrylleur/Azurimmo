import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.RetrofitInstance
import bts.sio.azurimmo.model.Appartement
import kotlinx.coroutines.launch

class AppartementViewModel : ViewModel() {
    // Liste mutable des appartements
    private val _appartements = mutableStateOf<List<Appartement>>(emptyList())
    val appartements: State<List<Appartement>> = _appartements

    // État de chargement
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // Gestion des messages d'erreur
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        // Charger les données initiales
        getAppartements()
    }

    private fun getAppartements() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Appel API pour récupérer directement un tableau d'appartements
                val response = RetrofitInstance.api.getAppartements()
                _appartements.value = response // Pas besoin d'extraire une clé
            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
