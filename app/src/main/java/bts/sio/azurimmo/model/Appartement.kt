package bts.sio.azurimmo.model

data class Appartement(
    val id: Int,
    val numero: Int,
    val surface: Double,
    val nbrePieces: Int,
    val description: String
)