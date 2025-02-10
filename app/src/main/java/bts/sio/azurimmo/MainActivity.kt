package bts.sio.azurimmo

import AppartementList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import bts.sio.azurimmo.ui.theme.AzurimmoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun AzurimmoApp() {
    AzurimmoTheme {
        // Sélectionnez le composable que vous voulez afficher
        // Par exemple : BatimentList ou AppartementList
        //BatimentList()
        // Si vous voulez afficher `AppartementList` à la place, remplacez par :
        //AppartementList(batimentId = batimentId)
        //ContratList()
        //InterventionList()
        //GarantList()
        //PaiementList()
        //LocataireList()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AzurimmoApp()
}
