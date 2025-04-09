package bts.sio.azurimmo.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToBatiments: () -> Unit,
    onNavigateToAppartements: () -> Unit,
    onNavigateToContrats: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenue sur Azur-Immo", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNavigateToBatiments,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voir les bâtiments")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToAppartements,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voir les appartements")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToContrats,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voir les contrats")
        }

    }
}