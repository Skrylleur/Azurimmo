package bts.sio.azurimmo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
import bts.sio.azurimmo.model.ContratRef
import bts.sio.azurimmo.model.Locataire
import bts.sio.azurimmo.repository.LocataireRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocataireViewModel : ViewModel() {

    private val repository = LocataireRepository(ApiClient.locataireApi)

    private val _locataires = MutableStateFlow<List<Locataire>>(emptyList())
    val locataires: StateFlow<List<Locataire>> = _locataires

    init {
        loadAll()
    }

    fun loadAll() {
        viewModelScope.launch {
            try {
                _locataires.value = repository.getAll()
            } catch (e: Exception) {
                println("❌ Erreur chargement locataires : ${e.message}")
            }
        }
    }

    fun loadParContrat(contratId: Long) {
        viewModelScope.launch {
            try {
                _locataires.value = repository.getByContrat(contratId)
            } catch (e: Exception) {
                println("❌ Erreur chargement locataires par contrat : ${e.message}")
            }
        }
    }

    fun addLocataire(nom: String, prenom: String, dateN: String, lieuN: String, contratId: Long) {
        viewModelScope.launch {
            try {
                val newLocataire = Locataire(
                    nom = nom,
                    prenom = prenom,
                    dateN = dateN,
                    lieuN = lieuN,
                    contrat = ContratRef(id = contratId)
                )
                println("🛰 Envoi du locataire : $newLocataire")
                val created = repository.create(newLocataire)
                println("✅ Locataire créé avec ID : ${created.id}")
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur ajout locataire : ${e.message}")
            }
        }
    }

    fun updateLocataire(id: Long, nom: String, prenom: String, dateN: String, lieuN: String, contratId: Long) {
        viewModelScope.launch {
            try {
                val updatedLocataire = Locataire(
                    id = id,
                    nom = nom,
                    prenom = prenom,
                    dateN = dateN,
                    lieuN = lieuN,
                    contrat = ContratRef(id = contratId)  // ✅ correction ici (virgule manquante)
                )
                repository.update(id, updatedLocataire)
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur modification locataire : ${e.message}")
            }
        }
    }

    fun deleteLocataire(id: Long) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadAll()
            } catch (e: Exception) {
                println("❌ Erreur suppression locataire : ${e.message}")
            }
        }
    }
}