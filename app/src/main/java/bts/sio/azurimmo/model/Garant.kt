package bts.sio.azurimmo.model

data class Garant(
    val id: Long? = null,
    val nom: String,
    val prenom: String,
    val contrat: ContratRef
)