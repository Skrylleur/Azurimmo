import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.util.Locale
import bts.sio.azurimmo.model.Intervention

@Composable
fun InterventionCard(intervention: Intervention) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val formattedDateIntervention = intervention.dateInter?.let { dateFormat.format(it) } ?: "Date non spécifiée"

    Text(
        text = "Description : ${intervention.description ?: "Pas de description"}",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "Type d'intervention : ${intervention.typeInter ?: "Pas de type d'intervention renseigné"}",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "Date d'intervention : $formattedDateIntervention",
        style = MaterialTheme.typography.bodyLarge
    )
}
