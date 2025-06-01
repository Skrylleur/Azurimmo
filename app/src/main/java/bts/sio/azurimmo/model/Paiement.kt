package bts.sio.azurimmo.model

data class Paiement(
    val id: Long? = null,
    val montant: Double,
    val datePaiement: String,
    val contrat: ContratRef
)