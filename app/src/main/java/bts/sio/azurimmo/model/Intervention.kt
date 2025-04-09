package bts.sio.azurimmo.model

data class Intervention(
    val id: Long = 0,
    val description: String,
    val typeInter: String,
    val dateInter: String
)
