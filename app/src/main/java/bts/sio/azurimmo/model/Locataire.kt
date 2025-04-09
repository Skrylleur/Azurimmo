package bts.sio.azurimmo.model

data class Locataire(
    val id: Long = 0,
    val nom: String,
    val prenom: String,
    val dateN: String,
    val lieuN: String
)