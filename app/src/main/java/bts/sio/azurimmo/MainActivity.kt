package bts.sio.azurimmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
            //InterventionList()
            //ContratList()
            //BatimentList()
            //AppartementList()
        }
    }
}
/*// Fonction pour prévisualiser l'interface
@Preview(showBackground = true)
@Composable
fun PreviewBatimentList() {
    BatimentList()
}
// Fonction pour prévisualiser l'interface
@Preview(showBackground = true)
@Composable
fun PreviewAppartementList() {
    AppartementList()
}
// Fonction pour prévisualiser l'interface
@Preview(showBackground = true)
@Composable
fun PreviewContratList() {
    ContratList()
}
*/