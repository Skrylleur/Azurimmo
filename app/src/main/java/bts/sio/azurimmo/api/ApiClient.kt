package bts.sio.azurimmo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    //Adresse de mon backend (API Spring Boot)
    private const val BASE_URL = "http://10.0.2.2:9008/api/" // ← Adresse de ton API

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // ← conversion JSON vers objet Kotlin
        .build()

    //Retrofit va créer un objet qui sait comment appeler chaque route HTTP définie dans BatimentApi.kt
    val batimentApi: BatimentApi = retrofit.create(BatimentApi::class.java)
    val appartementApi: AppartementApi = retrofit.create(AppartementApi::class.java)
    val contratApi: ContratApi = retrofit.create(ContratApi::class.java)
    val garantApi: GarantApi = retrofit.create(GarantApi::class.java)
}