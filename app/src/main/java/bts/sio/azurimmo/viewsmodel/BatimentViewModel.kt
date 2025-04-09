package bts.sio.azurimmo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
import bts.sio.azurimmo.model.Batiment
import bts.sio.azurimmo.repository.BatimentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BatimentViewModel : ViewModel() {

    // Connexion au Repository
    private val repository = BatimentRepository(ApiClient.batimentApi)

    // Flux de données : liste observable
    private val _batiments = MutableStateFlow<List<Batiment>>(emptyList())
    val batiments: StateFlow<List<Batiment>> = _batiments

    // Chargement initial à la création du ViewModel
    init {
        loadBatiments()
    }

    // 🔁 Fonction de chargement (GET)
    fun loadBatiments() {
        viewModelScope.launch {
            try {
                val response = repository.getAll()  // ✅ On récupère les données
                _batiments.value = response         // ✅ On met à jour le flux
                println("✅ Données récupérées : ${response.size} bâtiments")
            } catch (e: Exception) {
                println("Erreur chargement : ${e.message}")
                println("❌ Erreur réseau : ${e.message}")
            }
        }
    }

    // ➕ Fonction d'ajout (POST)
    fun addBatiment(adresse: String, ville: String) {
        viewModelScope.launch {
            try {
                repository.create(Batiment(adresse = adresse, ville = ville))
                loadBatiments()
            } catch (e: Exception) {
                println("Erreur création : ${e.message}")
            }
        }
    }

    // ✏️ Fonction de mise à jour (PUT)
    fun updateBatiment(id: Long, adresse: String, ville: String) {
        viewModelScope.launch {
            try {
                repository.update(id, Batiment(id = id, adresse = adresse, ville = ville))
                loadBatiments()
            } catch (e: Exception) {
                println("Erreur modification : ${e.message}")
            }
        }
    }

    // ❌ Fonction de suppression (DELETE)
    fun deleteBatiment(id: Long) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadBatiments()
            } catch (e: Exception) {
                println("Erreur suppression : ${e.message}")
            }
        }
    }
}