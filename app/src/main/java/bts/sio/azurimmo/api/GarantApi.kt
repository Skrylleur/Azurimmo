package bts.sio.azurimmo.api

import bts.sio.azurimmo.model.Garant
import retrofit2.http.*

interface GarantApi {

    @GET("garants")
    suspend fun getAll(): List<Garant>

    @GET("garants/contrat/{contratId}")
    suspend fun getByContrat(@Path("contratId") contratId: Long): List<Garant>

    @POST("garants")
    suspend fun create(@Body garant: Garant): Garant

    @PUT("garants/{id}")
    suspend fun update(@Path("id") id: Long, @Body garant: Garant):Garant

    @DELETE("garants/{id}")
    suspend fun delete(@Path("id") id: Long)
}