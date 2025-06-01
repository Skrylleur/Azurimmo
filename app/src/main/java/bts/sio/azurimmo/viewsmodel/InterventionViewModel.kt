package bts.sio.azurimmo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
import bts.sio.azurimmo.model.AppartementRef
import bts.sio.azurimmo.model.Intervention
import bts.sio.azurimmo.repository.InterventionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InterventionViewModel : ViewModel() {

    private val repository = InterventionRepository(ApiClient.interventionApi)

    private val _interventions = MutableStateFlow<List<Intervention>>(emptyList())
    val interventions: StateFlow<List<Intervention>> = _interventions

    init {
        loadAll()
    }

    fun loadAll() {
        viewModelScope.launch {
            try {
                _interventions.value = repository.getAll()
            } catch (e: Exception) {
                println("❌ Erreur chargement interventions : ${e.message}")
            }
        }
    }

    fun loadParAppartement(appartementId: Long) {
        viewModelScope.launch {
            try {
                _interventions.value = repository.getByAppartement(appartementId)
            } catch (e: Exception) {
                println("❌ Erreur chargement interventions par appartement : ${e.message}")
            }
        }
    }

    fun addIntervention(description: String, typeInter: String, dateInter: String, appartementId: Long) {
        viewModelScope.launch {
            try {
                val newIntervention = Intervention(
                    description = description,
                    typeInter = typeInter,
                    dateInter = dateInter,
                    appartement = AppartementRef(id = appartementId)
                )
                println("🛰 Envoi de l'intervention : $newIntervention")
                val created = repository.create(newIntervention)
                println("✅ Intervention créée avec ID : ${created.id}")
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur ajout intervention : ${e.message}")
            }
        }
    }

    fun updateIntervention(id: Long, description: String, typeInter: String, dateInter: String, appartementId: Long) {
        viewModelScope.launch {
            try {
                val updatedIntervention = Intervention(
                    id = id,
                    description = description,
                    typeInter = typeInter,
                    dateInter = dateInter,
                    appartement = AppartementRef(id = appartementId)
                )
                repository.update(id, updatedIntervention)
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur modification intervention : ${e.message}")
            }
        }
    }

    fun deleteIntervention(id: Long, appartementId: Long) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadParAppartement(appartementId)
            } catch (e: Exception) {
                println("❌ Erreur suppression intervention : ${e.message}")
            }
        }
    }
}