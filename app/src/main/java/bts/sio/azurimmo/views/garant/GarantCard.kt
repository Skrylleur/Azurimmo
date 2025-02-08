import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.util.Locale
import bts.sio.azurimmo.model.Garant

@Composable
fun GarantCard(garant: Garant) {

    Text(
        text = "Nom : ${garant.nom ?: "Pas de nom renseigné"}",
        style = MaterialTheme.typography.bodyLarge
    )
    Text(
        text = "Prénom : ${garant.prenom ?: "Pas de prénom renseigné"}",
        style = MaterialTheme.typography.bodyLarge
    )
}
