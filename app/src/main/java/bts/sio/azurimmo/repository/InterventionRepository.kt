package bts.sio.azurimmo.repository

import bts.sio.azurimmo.api.InterventionApi
import bts.sio.azurimmo.model.Intervention

class InterventionRepository(private val api: InterventionApi) {

    suspend fun getAll(): List<Intervention> = api.getAll()

    suspend fun getByAppartement(appartementId: Long): List<Intervention> =
        api.getByAppartement(appartementId)

    suspend fun create(intervention: Intervention): Intervention = api.create(intervention)

    suspend fun update(id: Long, intervention: Intervention): Intervention = api.update(id, intervention)

    suspend fun delete(id: Long) = api.delete(id)
}