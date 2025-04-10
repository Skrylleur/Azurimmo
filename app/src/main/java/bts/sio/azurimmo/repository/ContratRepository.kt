package bts.sio.azurimmo.repository


import bts.sio.azurimmo.api.ContratApi
import bts.sio.azurimmo.model.Contrat

class ContratRepository(private val api: ContratApi) {

    suspend fun getAll(): List<Contrat> {
        return api.getAll()
    }

    suspend fun create(contrat: Contrat): Contrat {
        return api.create(contrat)
    }

    suspend fun update(id: Long, contrat: Contrat): Contrat {
        return api.update(id, contrat)
    }

    suspend fun delete(id: Long) {
        api.delete(id)
    }
}