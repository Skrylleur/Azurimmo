import retrofit2.http.GET
import bts.sio.azurimmo.model.Batiment

interface ApiService {
    @GET("batiments")
    suspend fun getBatiments(): List<Batiment>
}
