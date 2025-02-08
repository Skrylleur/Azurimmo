package bts.sio.azurimmo.model

import android.content.ClipDescription
import java.util.Date

// Modèle pour une intervention
data class LocataireApiResponse(
    val embedded: EmbeddedContracts
)

data class Locataire(
    val id: Int,
    val nom: String,
    val prenom: String,
    val dateN: Date,
    val lieuN: String
)