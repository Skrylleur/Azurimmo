package bts.sio.azurimmo.repository

import bts.sio.azurimmo.api.BatimentApi
import bts.sio.azurimmo.model.Batiment

class BatimentRepository(private val api: BatimentApi) {

    suspend fun getAll(): List<Batiment> {
        return api.getAll()
    }

    suspend fun create(batiment: Batiment): Batiment {
        return api.create(batiment)
    }

    suspend fun update(id: Long, batiment: Batiment): Batiment {
        return api.update(id, batiment)
    }

    suspend fun delete(id: Long) {
        api.delete(id)
    }
}