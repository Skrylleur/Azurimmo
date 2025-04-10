package bts.sio.azurimmo.api

import bts.sio.azurimmo.model.Garant
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GarantApi {

    @GET("garants")
    suspend fun getAll(): List<Garant>

    @POST("garants")
    suspend fun create(@Body garant: Garant): Garant

    @PUT("garants/{id}")
    suspend fun update(@Path("id") id: Long, @Body garant: Garant): Garant

    @DELETE("garants/{id}")
    suspend fun delete(@Path("id") id: Long)
}