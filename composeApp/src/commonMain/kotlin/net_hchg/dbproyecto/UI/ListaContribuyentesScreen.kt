package net_hchg.dbproyecto.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net_hchg.dbproyecto.viewmodel.ContribuyentesViewModel

@Composable
fun ListaContribuyentesScreen(
    viewModel: ContribuyentesViewModel,
    onEditarFisica: (String) -> Unit,
    onEditarMoral: (String) -> Unit
) {
    val fisicas by viewModel.personasFisicas.collectAsState()
    val morales by viewModel.personasMorales.collectAsState()
    var tabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Directorio de Contribuyentes",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TabRow(selectedTabIndex = tabIndex) {
            Tab(
                selected = tabIndex == 0,
                onClick = { tabIndex = 0 },
                text = { Text("Personas Físicas") }
            )
            Tab(
                selected = tabIndex == 1,
                onClick = { tabIndex = 1 },
                text = { Text("Empresas (Morales)") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (tabIndex == 0) {
                items(fisicas) { persona ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(persona.nombre_completo, style = MaterialTheme.typography.titleMedium)
                                Text("RFC: ${persona.rfc}", style = MaterialTheme.typography.bodyMedium)
                                Text("CURP: ${persona.curp}", style = MaterialTheme.typography.bodyMedium)
                            }
                            Row {
                                IconButton(onClick = { onEditarFisica(persona.rfc) }) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Editar Fisica")
                                }
                                IconButton(onClick = { viewModel.eliminarPersonaFisica(persona.rfc) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Borrar Fisica", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            } else {
                items(morales) { empresa ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(empresa.razon_social, style = MaterialTheme.typography.titleMedium)
                                Text("RFC: ${empresa.rfc}", style = MaterialTheme.typography.bodyMedium)
                                Text("Rep. Legal: ${empresa.rfc_representante}", style = MaterialTheme.typography.bodyMedium)
                            }
                            Row {
                                IconButton(onClick = { onEditarMoral(empresa.rfc) }) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Editar Moral")
                                }
                                IconButton(onClick = { viewModel.eliminarPersonaMoral(empresa.rfc) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Borrar Moral", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}