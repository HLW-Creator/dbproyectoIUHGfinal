package net_hchg.dbproyecto.UI

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MenuPrincipalScreen(
    onNavigateToFisica: () -> Unit,
    onNavigateToMoral: () -> Unit,
    onNavigateToLista: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sistema de Gestión de Contribuyentes",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Botones del Menú
            MenuButton(
                text = "Inscripción Persona Física",
                icon = Icons.Default.Person,
                onClick = onNavigateToFisica
            )

            MenuButton(
                text = "Inscripción Persona Moral",
                icon = Icons.Default.Business,
                onClick = onNavigateToMoral
            )

            Divider(modifier = Modifier.padding(vertical = 24.dp))

            MenuButton(
                text = "Consultar Contribuyentes",
                icon = Icons.Default.List,
                onClick = onNavigateToLista,
                isSecondary = true
            )
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isSecondary: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp),
        shape = MaterialTheme.shapes.medium,
        colors = if (isSecondary) {
            ButtonDefaults.outlinedButtonColors()
        } else {
            ButtonDefaults.buttonColors()
        }
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}