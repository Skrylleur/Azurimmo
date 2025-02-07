import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import bts.sio.azurimmo.model.Batiment
import bts.sio.azurimmo.model.Contrat

@Composable
fun ContratCard(contrat: Contrat) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            val dateFormat = SimpleDateFormat("dd MMMM yyyy")

            val formattedDateEntree = dateFormat.format(contrat.dateEntree)
            val formattedDateSortie = dateFormat.format(contrat.dateSortie)
            // Afficher les valeurs du contrat, avec gestion des nulls et conversion si nécessaire
            Text(
                text = "Date d'entrée : $formattedDateEntree",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Date de sortie : $formattedDateSortie",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Charges : ${contrat.montantCharges?.toString() ?: "Non spécifié"} €",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Loyer : ${contrat.montantLoyer?.toString() ?: "Non spécifié"} €",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
