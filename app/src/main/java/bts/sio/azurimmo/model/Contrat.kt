package bts.sio.azurimmo.model

data class Contrat(
    val id: Long = 0,
    val dateEntree: String,  // format "yyyy-MM-dd"
    val dateSortie: String,
    val montantLoyer: Double,
    val montantCharges: Double,
    val statut: String
)