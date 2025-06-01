package bts.sio.azurimmo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
import bts.sio.azurimmo.model.Appartement
import bts.sio.azurimmo.model.BatimentRef
import bts.sio.azurimmo.repository.AppartementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppartementViewModel : ViewModel() {

    private val repository = AppartementRepository(ApiClient.appartementApi)

    private val _appartements = MutableStateFlow<List<Appartement>>(emptyList())
    val appartements: StateFlow<List<Appartement>> = _appartements

    private val _appartementActuel = MutableStateFlow<Appartement?>(null)
    val appartementActuel: StateFlow<Appartement?> = _appartementActuel

    init {
        loadAppartements()
    }

    fun loadAppartements() {
        viewModelScope.launch {
            try {
                val response = repository.getAll()
                _appartements.value = response
                println("✅ Données récupérées : ${response.size} appartements")
            } catch (e: Exception) {
                println("❌ Erreur chargement : ${e.message}")
            }
        }
    }

    fun loadAppartementById(id: Long, onLoaded: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val result = repository.getAppartementById(id)
                _appartementActuel.value = result
                _appartements.value =
                    _appartements.value.filterNot { it.id == id } + result
                onLoaded?.invoke()
            } catch (e: Exception) {
                Log.e("ViewModel", "Erreur chargement appartement : ${e.message}")
            }
        }
    }

    fun loadParBatiment(batimentId: Long) {
        viewModelScope.launch {
            try {
                _appartements.value = repository.getByBatiment(batimentId)
                println("✅ Appartements chargés pour le bâtiment $batimentId")
            } catch (e: Exception) {
                println("❌ Erreur chargement appartements du bâtiment : ${e.message}")
            }
        }
    }

    fun addAppartement(numero: Int, surface: Float, nbPieces: Int, description: String, batimentId: Long) {
        viewModelScope.launch {
            try {
                val appartement = Appartement(
                    numero = numero,
                    surface = surface,
                    nbPieces = nbPieces,
                    description = description,
                    batiment = BatimentRef(batimentId)
                )
                val nouveau = repository.create(appartement)
                _appartements.value = _appartements.value + nouveau
                println("✅ Appartement ajouté")
            } catch (e: Exception) {
                println("❌ Erreur ajout appartement : ${e.message}")
            }
        }
    }

    fun deleteAppartement(id: Long) {
        viewModelScope.launch {
            try {
                if (repository.deleteAppartement(id)) {
                    _appartements.value = _appartements.value.filterNot { it.id == id }
                    println("✅ Appartement supprimé")
                }
            } catch (e: Exception) {
                println("❌ Erreur suppression appartement : ${e.message}")
            }
        }
    }

    fun updateAppartement(
        id: Long,
        numero: Int,
        surface: Float,
        nbPieces: Int,
        description: String,
        batimentId: Long
    ) {
        viewModelScope.launch {
            try {
                val updatedAppartement = Appartement(
                    id = id,
                    numero = numero,
                    surface = surface,
                    nbPieces = nbPieces,
                    description = description,
                    batiment = BatimentRef(batimentId)
                )

                val result = repository.update(id, updatedAppartement)

                if (result != null) {
                    println("✅ Appartement modifié avec succès")
                    _appartements.value = _appartements.value
                        .filterNot { it.id == id } + result
                } else {
                    println("❌ La mise à jour a échoué (objet null)")
                }
            } catch (e: Exception) {
                println("❌ Erreur modification : ${e.message}")
            }
        }
    }
}