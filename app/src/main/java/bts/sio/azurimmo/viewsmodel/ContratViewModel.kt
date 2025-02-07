import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import bts.sio.azurimmo.model.Batiment
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.RetrofitInstance
import bts.sio.azurimmo.model.Contrat
import kotlinx.coroutines.launch
class ContratViewModel : ViewModel() {
    // Liste mutable des bâtiments
    private val _contrats = mutableStateOf<List<Contrat>>(emptyList())
    val contrats: State<List<Contrat>> = _contrats
    private val _isLoading = mutableStateOf(false)

    val isLoading: State<Boolean> = _isLoading
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage
    init {
// Simuler un chargement de données initiales
        getContrats()
    }

    private fun getContrats() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getContrats()
                _contrats.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
            } finally {
                _isLoading.value = false
                println("pas de chargement")
            }
        }
    }
}