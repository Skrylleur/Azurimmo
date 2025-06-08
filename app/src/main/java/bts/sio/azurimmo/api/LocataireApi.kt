package bts.sio.azurimmo.api

import bts.sio.azurimmo.model.Locataire
import retrofit2.http.*

interface LocataireApi {

    @GET("locataires")
    suspend fun getAll(): List<Locataire>

    @GET("locataires/contrat/{contratId}")
    suspend fun getByContrat(@Path("contratId") contratId: Long): List<Locataire>

    @POST("locataires")
    suspend fun create(@Body locataire: Locataire): Locataire

    @PUT("locataires/{id}")
    suspend fun update(@Path("id") id: Long, @Body locataire: Locataire): Locataire

    @DELETE("locataires/{id}")
    suspend fun delete(@Path("id") id: Long)
}