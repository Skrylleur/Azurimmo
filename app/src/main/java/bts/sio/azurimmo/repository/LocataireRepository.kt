package bts.sio.azurimmo.repository

import bts.sio.azurimmo.api.LocataireApi
import bts.sio.azurimmo.model.Locataire

class LocataireRepository(private val api: LocataireApi) {

    suspend fun getAll(): List<Locataire> = api.getAll()

    suspend fun getByContrat(contratId: Long): List<Locataire> =
        api.getByContrat(contratId)

    suspend fun create(locataire: Locataire): Locataire = api.create(locataire)

    suspend fun update(id: Long, locataire: Locataire): Locataire = api.update(id, locataire)

    suspend fun delete(id: Long) = api.delete(id)
}