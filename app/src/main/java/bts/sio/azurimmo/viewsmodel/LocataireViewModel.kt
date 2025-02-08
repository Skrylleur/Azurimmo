import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.RetrofitInstance
import bts.sio.azurimmo.model.Locataire
import kotlinx.coroutines.launch

class LocataireViewModel : ViewModel() {

    // Liste mutable des interventions
    private val _locataires = mutableStateOf<List<Locataire>>(emptyList())
    val locataires: State<List<Locataire>> = _locataires

    // État de chargement
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // Message d'erreur
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        // Charger les interventions au démarrage
        getLocataires()
    }

    private fun getLocataires() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Appel de l'API
                val response = RetrofitInstance.api.getLocataires()
                _locataires.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
