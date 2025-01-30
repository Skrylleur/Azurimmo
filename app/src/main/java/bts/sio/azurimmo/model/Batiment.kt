package bts.sio.azurimmo.model
// Modèle pour un bâtiment
data class ApiResponse(
    val _embedded: EmbeddedBatiments
)

data class EmbeddedBatiments(
    val batiments: List<Batiment>
)

data class Batiment(
    val id: Int,
    val adresse: String,
    val ville: String
)
