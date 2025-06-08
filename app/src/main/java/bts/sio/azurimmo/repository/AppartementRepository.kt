package bts.sio.azurimmo.repository

import bts.sio.azurimmo.api.AppartementApi
import bts.sio.azurimmo.model.Appartement
import bts.sio.azurimmo.model.Batiment

class AppartementRepository(private val api: AppartementApi) {
    suspend fun getAll() = api.getAll()
    suspend fun getById(id: Long) = api.getById(id)
    suspend fun getByBatiment(batimentId: Long) = api.getByBatiment(batimentId)
    suspend fun create(appartement: Appartement) = api.create(appartement)
    suspend fun getAppartementById(id: Long): Appartement {
        return api.getById(id)
    }
    suspend fun update(id: Long, appartement: Appartement): Appartement {
        return api.update(id, appartement)
    }

    suspend fun deleteAppartement(id: Long): Boolean {
        return try {
            api.delete(id)
            true
        } catch (e: Exception) {
            println("❌ Erreur suppression appartement : ${e.message}")
            false
        }
    }
}