package bts.sio.azurimmo.api

import Contrat
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContratApi {

    @GET("contrats")
    suspend fun getAll(): List<Contrat>

    @POST("contrats")
    suspend fun create(@Body contrat: Contrat): Contrat

    @PUT("contrats/{id}")
    suspend fun update(@Path("id") id: Long, @Body contrat: Contrat): Contrat

    @DELETE("contrats/{id}")
    suspend fun delete(@Path("id") id: Long)
}