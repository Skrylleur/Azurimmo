package bts.sio.azurimmo.repository

import bts.sio.azurimmo.api.PaiementApi
import bts.sio.azurimmo.model.Paiement

class PaiementRepository(private val api: PaiementApi) {

    suspend fun getAll(): List<Paiement> = api.getAll()

    suspend fun getByContrat(contratId: Long): List<Paiement> =
        api.getByContrat(contratId)

    suspend fun create(paiement: Paiement): Paiement = api.create(paiement)

    suspend fun update(id: Long, paiement: Paiement): Paiement = api.update(id, paiement)

    suspend fun delete(id: Long) = api.delete(id)
}