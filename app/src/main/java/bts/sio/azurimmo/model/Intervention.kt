package bts.sio.azurimmo.model

import android.content.ClipDescription
import java.util.Date

// Modèle pour une intervention
data class InterventionApiResponse(
    val embedded: EmbeddedContracts
)

data class Intervention(
    val id: Int,
    val description: String,
    val typeInter: String,
    val dateInter: Date
)
