package bts.sio.azurimmo.api

import bts.sio.azurimmo.model.Paiement
import retrofit2.http.*

interface PaiementApi {

    @GET("paiements")
    suspend fun getAll(): List<Paiement>

    @GET("paiements/contrat/{contratId}")
    suspend fun getByContrat(@Path("contratId") contratId: Long): List<Paiement>

    @POST("paiements")
    suspend fun create(@Body paiement: Paiement): Paiement

    @PUT("paiements/{id}")
    suspend fun update(@Path("id") id: Long, @Body paiement: Paiement): Paiement

    @DELETE("paiements/{id}")
    suspend fun delete(@Path("id") id: Long)
}