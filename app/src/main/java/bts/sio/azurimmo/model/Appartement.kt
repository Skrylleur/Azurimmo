package bts.sio.azurimmo.model

data class Appartement(
    val id: Long = 0,
    val numero: String,
    val surface: String,
    val nb_pieces: String,
    val description: String
)
