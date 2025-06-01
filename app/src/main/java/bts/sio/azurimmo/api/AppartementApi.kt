package bts.sio.azurimmo.api

import bts.sio.azurimmo.model.Appartement
import retrofit2.http.*

interface AppartementApi {

    @GET("appartements")
    suspend fun getAll(): List<Appartement>

    @GET("appartements/{id}")
    suspend fun getById(@Path("id") id: Long): Appartement

    @GET("appartements/batiment/{batimentId}")
    suspend fun getByBatiment(@Path("batimentId") batimentId: Long): List<Appartement>

    @POST("appartements")
    suspend fun create(@Body appartement: Appartement): Appartement

    @PUT("appartements/{id}")
    suspend fun update(@Path("id") id: Long, @Body appartement: Appartement): Appartement

    @DELETE("appartements/{id}")
    suspend fun delete(@Path("id") id: Long)
}