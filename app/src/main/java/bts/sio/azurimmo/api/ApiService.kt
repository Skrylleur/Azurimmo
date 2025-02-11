import bts.sio.azurimmo.model.Appartement
import retrofit2.http.GET
import bts.sio.azurimmo.model.Batiment
import bts.sio.azurimmo.model.Contrat
import bts.sio.azurimmo.model.Garant
import bts.sio.azurimmo.model.Intervention
import bts.sio.azurimmo.model.Locataire
import bts.sio.azurimmo.model.Paiement
import retrofit2.http.Path

interface ApiService {
    @GET("api/batiments/")
    suspend fun getBatiments(): List<Batiment>

    @GET("api/appartements/")
    suspend fun getAppartements(): List<Appartement>

    @GET("api/contrats/")
    suspend fun getContrats(): List<Contrat>

    @GET("api/interventions/")
    suspend fun getInterventions(): List<Intervention>

    @GET("api/garants/")
    suspend fun getGarants(): List<Garant>

    @GET("api/paiements/")
    suspend fun getPaiements(): List<Paiement>

    @GET("api/locataires/")
    suspend fun getLocataires(): List<Locataire>

    @GET("api/batiments/{id}")
    suspend fun getBatimentById(@Path("id") id: Int): Batiment

    @GET("/api/appartements/batiment/{batimentId}")
    suspend fun getAppartementsByBatimentId(@Path("batimentId") batimentId: Int): List<Appartement>

}
