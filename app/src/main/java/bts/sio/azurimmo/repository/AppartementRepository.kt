package bts.sio.azurimmo.repository

import bts.sio.azurimmo.api.AppartementApi
import bts.sio.azurimmo.model.Appartement

class AppartementRepository(private val api: AppartementApi) {

    suspend fun getAll(): List<Appartement> {
        return api.getAll()
    }

    suspend fun create(appartement: Appartement): Appartement {
        return api.create(appartement)
    }

    suspend fun update(id: Long, appartement: Appartement): Appartement {
        return api.update(id, appartement)
    }

    suspend fun delete(id: Long) {
        api.delete(id)
    }
}