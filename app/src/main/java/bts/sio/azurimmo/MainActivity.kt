package bts.sio.azurimmo

import BatimentList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import bts.sio.azurimmo.ui.theme.AzurimmoTheme
import bts.sio.azurimmo.views.appartement.AppartementList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
// Appel de la fonction BatimentList
            //BatimentList()
//Appel de la fonction AppartementList
            AppartementList()
        }
    }
}

// Fonction pour prévisualiser l'interface
@Preview(showBackground = true)
@Composable
fun PreviewBatimentList() {
    //BatimentList()
    AppartementList()
}
