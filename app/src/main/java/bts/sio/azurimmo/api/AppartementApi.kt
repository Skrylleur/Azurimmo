package bts.sio.azurimmo.api

import bts.sio.azurimmo.model.Appartement
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AppartementApi {

    @GET("appartements")
    suspend fun getAll(): List<Appartement>

    @POST("appartements")
    suspend fun create(@Body appartement: Appartement): Appartement

    @PUT("appartements/{id}")
    suspend fun update(@Path("id") id: Long, @Body appartement: Appartement): Appartement

    @DELETE("appartements/{id}")
    suspend fun delete(@Path("id") id: Long)
}