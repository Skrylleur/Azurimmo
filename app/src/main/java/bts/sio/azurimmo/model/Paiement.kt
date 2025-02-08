package bts.sio.azurimmo.model

import android.content.ClipDescription
import java.util.Date

// Modèle pour un paiement
data class PaiementApiResponse(
    val embedded: EmbeddedContracts
)

data class Paiement(
    val id: Int,
    val montant: Double,
    val datePaiement: Date
)