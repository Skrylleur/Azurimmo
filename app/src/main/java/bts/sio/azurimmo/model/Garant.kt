package bts.sio.azurimmo.model

import android.content.ClipDescription

// Modèle pour une intervention
data class GarantApiResponse(
    val embedded: EmbeddedContracts
)

data class Garant(
    val id: Int,
    val nom: String,
    val prenom: String
)