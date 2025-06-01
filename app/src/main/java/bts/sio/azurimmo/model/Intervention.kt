package bts.sio.azurimmo.model

data class Intervention(
    val id: Long? = null,
    val description: String,
    val typeInter: String,
    val dateInter: String,
    val appartement: AppartementRef
)