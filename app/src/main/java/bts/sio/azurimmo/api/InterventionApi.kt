package bts.sio.azurimmo.api

import bts.sio.azurimmo.model.Intervention
import retrofit2.http.*

interface InterventionApi {

    @GET("interventions")
    suspend fun getAll(): List<Intervention>

    @GET("interventions/appartement/{appartementId}")
    suspend fun getByAppartement(@Path("appartementId") appartementId: Long): List<Intervention>

    @POST("interventions")
    suspend fun create(@Body intervention: Intervention): Intervention

    @PUT("interventions/{id}")
    suspend fun update(@Path("id") id: Long, @Body intervention: Intervention): Intervention

    @DELETE("interventions/{id}")
    suspend fun delete(@Path("id") id: Long)
}