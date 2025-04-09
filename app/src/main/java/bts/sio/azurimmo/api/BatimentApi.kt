package bts.sio.azurimmo.api

//J'importe le model car je vais l'utiliser dans mes fonctions
import bts.sio.azurimmo.model.Batiment
//import des anotations de RetroFit nécessaires
//@GET, @POST, @PUT, @DELETE pour indiquer le type de requête HTTP
//@Body pour dire que j'envoie un objet dans le corps de la requête
//@Path pour dire que j'insère une valeur dynamique dans l'URL (ex: l'id)
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BatimentApi {

    //Fais une requête GET vers /batiments et récupère la liste des batiments (comme une méthode findAll() dans Sping Boot
    @GET("batiments")
    suspend fun getAll(): List<Batiment>

    //Fais une requête POST vers /batiments et envoie un Batiment au format JSON dans le corps de la requête
    //Retourne le batiment créé (avec l'id généré par le serveur)
    @POST("batiments")
    suspend fun create(@Body batiment: Batiment): Batiment

    //Fais une requête PUT vers /batiments/14 si id = 14
    //Envoie le nouvel objet Batiment mis à jour dans le corps
    @PUT("batiments/{id}")
    suspend fun update(@Path("id") id: Long, @Body batiment: Batiment): Batiment

    //Fais une requête DELETE vers /batiments/14 pour supprimer pour le bâtiment 14
    @DELETE("batiments/{id}")
    suspend fun delete(@Path("id") id: Long)
}