package bts.sio.azurimmo.model

data class Contrat(
    val id: Long = 0,
    val dateEntree: String,
    val dateSortie: String,
    val montantLoyer: String,
    val montantCharges: String,
    val statut: String
)