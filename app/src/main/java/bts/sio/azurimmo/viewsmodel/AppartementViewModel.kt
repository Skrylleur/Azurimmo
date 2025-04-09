package bts.sio.azurimmo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.ApiClient
import bts.sio.azurimmo.model.Appartement
import bts.sio.azurimmo.repository.AppartementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppartementViewModel : ViewModel() {

    // Connexion au Repository
    private val repository = AppartementRepository(ApiClient.appartementApi)

    // Flux de données : liste observable
    private val _appartements = MutableStateFlow<List<Appartement>>(emptyList())
    val appartements: StateFlow<List<Appartement>> = _appartements

    // Chargement initial à la création du ViewModel
    init {
        loadAppartements()
    }

    // 🔁 Fonction de chargement (GET)
    fun loadAppartements() {
        viewModelScope.launch {
            try {
                val response = repository.getAll()  // ✅ On récupère les données
                _appartements.value = response         // ✅ On met à jour le flux
                println("✅ Données récupérées : ${response.size} appartements")
            } catch (e: Exception) {
                println("Erreur chargement : ${e.message}")
                println("❌ Erreur réseau : ${e.message}")
            }
        }
    }

    // ➕ Fonction d'ajout (POST)
    fun addAppartement(numero: String, surface: String, nb_pieces: String, description: String, batiment_id: Long) {
        viewModelScope.launch {
            try {
                repository.create(Appartement(numero = numero, surface = surface, nb_pieces = nb_pieces, description = description, batiment_id = batiment_id))
                loadAppartements()
            } catch (e: Exception) {
                println("Erreur création : ${e.message}")
            }
        }
    }

    // ✏️ Fonction de mise à jour (PUT)
    fun updateAppartement(id: Long, numero: String, surface: String, nb_pieces: String, description: String, batiment_id: Long) {
        viewModelScope.launch {
            try {
                repository.update(id, Appartement(id = id, numero = numero, surface = surface, nb_pieces = nb_pieces, description = description, batiment_id = batiment_id))
                loadAppartements()
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
                loadAppartements()
            } catch (e: Exception) {
                println("Erreur suppression : ${e.message}")
            }
        }
    }
}