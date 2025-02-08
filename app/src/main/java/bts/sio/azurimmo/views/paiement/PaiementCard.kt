import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import java.text.SimpleDateFormat
import java.util.Locale
import bts.sio.azurimmo.model.Paiement

@Composable
fun PaiementCard(paiement: Paiement) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val formattedDatePaiement = paiement.datePaiement?.let { dateFormat.format(it) } ?: "Date non spécifiée"

    Text(
        text = "Montant : ${paiement.montant ?: "Pas de montant renseigné."}",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "Date de paiement : $formattedDatePaiement",
        style = MaterialTheme.typography.bodyLarge
    )
}
