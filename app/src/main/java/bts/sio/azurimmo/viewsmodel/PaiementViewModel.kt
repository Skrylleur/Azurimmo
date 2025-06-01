package bts.sio.azurimmo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
import bts.sio.azurimmo.model.Contrat
import bts.sio.azurimmo.model.ContratRef
import bts.sio.azurimmo.model.Paiement
import bts.sio.azurimmo.repository.PaiementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaiementViewModel : ViewModel() {

    private val repository = PaiementRepository(ApiClient.paiementApi)

    private val _paiements = MutableStateFlow<List<Paiement>>(emptyList())
    val paiements: StateFlow<List<Paiement>> = _paiements

    init {
        loadAll()
    }

    fun loadAll() {
        viewModelScope.launch {
            try {
                _paiements.value = repository.getAll()
            } catch (e: Exception) {
                println("Erreur chargement paiements : ${e.message}")
            }
        }
    }

    fun loadParContrat(contratId: Long) {
        viewModelScope.launch {
            try {
                _paiements.value = repository.getByContrat(contratId)
            } catch (e: Exception) {
                println("Erreur chargement paiements par contrat : ${e.message}")
            }
        }
    }

    fun addPaiement(montant: Double, datePaiement: String, contratId: Long) {
        viewModelScope.launch {
            try {
                val newPaiement = Paiement(
                    montant = montant,
                    datePaiement = datePaiement,
                    contrat = ContratRef(id = contratId) // <- assure-toi d’avoir importé ContratRef
                )
                println("🛰 Envoi du paiement : $newPaiement")
                val created = repository.create(newPaiement)
                println("✅ Paiement créé avec ID : ${created.id}")
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur ajout paiement : ${e.message}")
            }
        }
    }

    fun updatePaiement(id: Long, montant: Double, datePaiement: String, contratId: Long) {
        viewModelScope.launch {
            try {
                val updatedPaiement = Paiement(
                    id = id,
                    montant = montant,
                    datePaiement = datePaiement,
                    contrat = ContratRef(id = contratId)
                )
                repository.update(id, updatedPaiement)
                loadAll()
            } catch (e: Exception) {
                println("Erreur modification paiement : ${e.message}")
            }
        }
    }

    fun deletePaiement(id: Long) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadAll()
            } catch (e: Exception) {
                println("Erreur suppression paiement : ${e.message}")
            }
        }
    }
}