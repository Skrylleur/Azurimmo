import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import java.text.SimpleDateFormat
import java.util.Locale
import bts.sio.azurimmo.model.Locataire

@Composable
fun LocataireCard(locataire: Locataire) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val formattedDateDateN = locataire.dateN?.let { dateFormat.format(it) } ?: "Date non spécifiée"

    Text(
        text = "Nom : ${locataire.nom ?: "Pas de nom renseigné."}",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "Prénom : ${locataire.prenom ?: "Pas prénom renseigné"}",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "DateN : $formattedDateDateN",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "LienN : ${locataire.lieuN ?: "Pas de lieuN renseigné."}",
        style = MaterialTheme.typography.bodyLarge
    )
}
