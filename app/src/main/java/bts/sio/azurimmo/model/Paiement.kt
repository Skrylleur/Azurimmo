package bts.sio.azurimmo.model

data class Paiement(
    val id: Int,
    val montant: Double,
    val datePaiement: String
)