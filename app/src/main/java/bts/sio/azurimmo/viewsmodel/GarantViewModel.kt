package bts.sio.azurimmo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
import bts.sio.azurimmo.model.ContratRef
import bts.sio.azurimmo.model.Garant
import bts.sio.azurimmo.model.Locataire
import bts.sio.azurimmo.repository.GarantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GarantViewModel : ViewModel() {

    private val repository = GarantRepository(ApiClient.garantApi)

    private val _garants = MutableStateFlow<List<Garant>>(emptyList())
    val garants: StateFlow<List<Garant>> = _garants

    init {
        loadAll()
    }

    fun loadAll() {
        viewModelScope.launch {
            try {
                _garants.value = repository.getAll()
            } catch (e: Exception) {
                println("❌ Erreur chargement garants : ${e.message}")
            }
        }
    }

    fun loadParContrat(contratId: Long) {
        viewModelScope.launch {
            try {
                _garants.value = repository.getByContrat(contratId)
            } catch (e: Exception) {
                println("❌ Erreur chargement garants par contrat : ${e.message}")
            }
        }
    }

    fun addGarant(nom: String, prenom: String, contratId: Long) {
        viewModelScope.launch {
            try {
                val newGarant = Garant(
                    nom = nom,
                    prenom = prenom,
                    contrat = ContratRef(id = contratId)
                )
                println("🛰 Envoi du garant : $newGarant")
                val created = repository.create(newGarant)
                println("✅ Garant créé avec ID : ${created.id}")
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur ajout garant : ${e.message}")
            }
        }
    }

    fun updateGarant(id: Long, nom: String, prenom: String, contratId: Long) {
        viewModelScope.launch {
            try {
                val updatedGarant = Garant(
                    id = id,
                    nom = nom,
                    prenom = prenom,
                    contrat = ContratRef(id = contratId)
                )
                repository.update(id, updatedGarant)
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur modification garant : ${e.message}")
            }
        }
    }

    fun deleteGarant(id: Long) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur suppression garant : ${e.message}")
            }
        }
    }
}