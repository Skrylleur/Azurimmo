package bts.sio.azurimmo.model

import java.util.Date

// Modèle pour un contrat
data class ContratApiResponse(
    val embedded: EmbeddedContracts
)

class EmbeddedContracts {

}


data class EmbeddedContrats(
    val contrats: List<Contrat>
)

data class Contrat(
    val id: Int,
    val dateEntree: Date,
    val dateSortie: Date,
    val montantLoyer: Double,
    val montantCharges: Double,
    val statut: String
)
