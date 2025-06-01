package bts.sio.azurimmo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
import bts.sio.azurimmo.model.AppartementRef
import bts.sio.azurimmo.model.Contrat
import bts.sio.azurimmo.repository.ContratRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContratViewModel : ViewModel() {

    private val repository = ContratRepository(ApiClient.contratApi)

    private val _contrats = MutableStateFlow<List<Contrat>>(emptyList())
    val contrats: StateFlow<List<Contrat>> = _contrats

    init {
        loadContrats()
    }

    fun loadContrats() {
        viewModelScope.launch {
            try {
                _contrats.value = repository.getAll()
            } catch (e: Exception) {
                println("❌ Erreur chargement contrats : ${e.message}")
            }
        }
    }

    fun loadParAppartement(appartementId: Long) {
        viewModelScope.launch {
            try {
                _contrats.value = repository.getByAppartement(appartementId)
            } catch (e: Exception) {
                println("❌ Erreur chargement contrats par appartement : ${e.message}")
            }
        }
    }

    fun addContrat(
        dateEntree: String,
        dateSortie: String,
        montantLoyer: String,
        montantCharges: String,
        statut: String,
        appartementId: Long
    ) {
        viewModelScope.launch {
            try {
                val contrat = Contrat(
                    dateEntree = dateEntree,
                    dateSortie = dateSortie,
                    montantLoyer = montantLoyer.toDouble().toString(),
                    montantCharges = montantCharges.toDouble().toString(),
                    statut = statut,
                    appartement = AppartementRef(appartementId)
                )
                repository.create(contrat)
                loadContrats()
            } catch (e: Exception) {
                println("❌ Erreur ajout contrat : ${e.message}")
            }
        }
    }

    fun updateContrat(
        id: Long,
        dateEntree: String,
        dateSortie: String,
        montantLoyer: String,
        montantCharges: String,
        statut: String,
        appartementId: Long
    ) {
        viewModelScope.launch {
            try {
                val contrat = Contrat(
                    id = id,
                    dateEntree = dateEntree,
                    dateSortie = dateSortie,
                    montantLoyer = montantLoyer.toDouble().toString(),
                    montantCharges = montantCharges.toDouble().toString(),
                    statut = statut,
                    appartement = AppartementRef(appartementId)
                )
                repository.update(id, contrat)
                loadContrats()
            } catch (e: Exception) {
                println("❌ Erreur modification contrat : ${e.message}")
            }
        }
    }

    fun deleteContrat(id: Long) {
        viewModelScope.launch {
            if (repository.deleteContrat(id)) {
                _contrats.value = _contrats.value.filterNot { it.id == id }
            }
        }
    }
}