package bts.sio.azurimmo.model

data class Locataire(
    val id: Long? = null,
    val nom: String,
    val prenom: String,
    val dateN: String,
    val lieuN: String,
    val contrat: ContratRef
)