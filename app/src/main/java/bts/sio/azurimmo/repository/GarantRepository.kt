package bts.sio.azurimmo.repository

import bts.sio.azurimmo.api.GarantApi
import bts.sio.azurimmo.model.Garant

class GarantRepository(private val api: GarantApi) {

    suspend fun getAll(): List<Garant> {
        return api.getAll()
    }

    suspend fun create(garant: Garant): Garant {
        return api.create(garant)
    }

    suspend fun update(id: Long, garant: Garant): Garant {
        return api.update(id, garant)
    }

    suspend fun delete(id: Long) {
        api.delete(id)
    }
}