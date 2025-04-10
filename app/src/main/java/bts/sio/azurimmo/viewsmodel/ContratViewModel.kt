package bts.sio.azurimmo.viewmodel

import bts.sio.azurimmo.model.Contrat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
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
                val response = repository.getAll()
                _contrats.value = response
                println("✅ Données récupérées : ${response.size} contrats")
            } catch (e: Exception) {
                println("❌ Erreur chargement : ${e.message}")
            }
        }
    }

    fun addContrat(dateEntree: String, dateSortie: String, montantLoyer: String, montantCharges: String, statut: String) {
        viewModelScope.launch {
            try {
                val contrat = Contrat(
                    dateEntree = dateEntree,
                    dateSortie = dateSortie,
                    montantLoyer = montantLoyer.toDouble().toString(),
                    montantCharges = montantCharges.toDouble().toString(),
                    statut = statut
                )
                repository.create(contrat)
                loadContrats()
            } catch (e: Exception) {
                println("❌ Erreur création : ${e.message}")
            }
        }
    }

    fun updateContrat(id: Long, dateEntree: String, dateSortie: String, montantLoyer: String, montantCharges: String, statut: String) {
        viewModelScope.launch {
            try {
                val contrat = Contrat(
                    id = id,
                    dateEntree = dateEntree,
                    dateSortie = dateSortie,
                    montantLoyer = montantLoyer.toDouble().toString(),
                    montantCharges = montantCharges.toDouble().toString(),
                    statut = statut
                )
                repository.update(id, contrat)
                loadContrats()
            } catch (e: Exception) {
                println("❌ Erreur modification : ${e.message}")
            }
        }
    }

    fun deleteContrat(id: Long) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadContrats()
            } catch (e: Exception) {
                println("❌ Erreur suppression : ${e.message}")
            }
        }
    }
}