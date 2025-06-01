package bts.sio.azurimmo.api

import bts.sio.azurimmo.model.Contrat
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContratApi {

    @GET("contrats")
    suspend fun getAll(): List<Contrat>

    @GET("contrats/appartement/{appartementId}")
    suspend fun getByAppartement(@Path("id") appartementId: Long): List<Contrat>

    @POST("contrats")
    suspend fun create(@Body contrat: Contrat): Contrat

    @PUT("contrats/{id}")
    suspend fun update(@Path("id") id: Long, @Body contrat: Contrat): Contrat

    @DELETE("contrats/{id}")
    suspend fun deleteContrat(@Path("id") id: Long): Response<Void>
}