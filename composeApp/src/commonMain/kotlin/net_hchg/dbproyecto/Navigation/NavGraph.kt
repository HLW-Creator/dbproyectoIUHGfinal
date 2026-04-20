package net_hchg.dbproyecto.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net_hchg.dbproyecto.UI.MenuPrincipalScreen
import net_hchg.dbproyecto.UI.RegistroPersonaFisicaScreen
import net_hchg.dbproyecto.UI.RegistroPersonaMoralScreen
import net_hchg.dbproyecto.UI.ListaContribuyentesScreen
import net_hchg.dbproyecto.UI.RegistroDomicilioFiscalScreen
import net_hchg.dbproyecto.viewmodel.ContribuyentesViewModel

@Composable
fun NavGraph(viewModel: ContribuyentesViewModel) {
    var pantallaActual by remember { mutableStateOf("menu") }
    var rfcAEditar by remember { mutableStateOf<String?>(null) }
    var rfcParaDomicilio by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        if (pantallaActual != "menu") {
            FilledTonalButton(
                onClick = { pantallaActual = "menu" },
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            ) {
                Text("← Regresar al Menú")
            }
        }

        when (pantallaActual) {
            "menu" -> MenuPrincipalScreen(
                onNavigateToFisica = { rfcAEditar = null; pantallaActual = "registro_fisica" },
                onNavigateToMoral = { rfcAEditar = null; pantallaActual = "registro_moral" },
                onNavigateToLista = { pantallaActual = "lista" }
            )
            "registro_fisica" -> RegistroPersonaFisicaScreen(
                viewModel = viewModel,
                rfcRecibido = rfcAEditar,
                onNavigateNext = { rfc ->
                    rfcParaDomicilio = rfc
                    pantallaActual = "registro_domicilio"
                }
            )
            "registro_moral" -> RegistroPersonaMoralScreen(
                viewModel = viewModel,
                rfcRecibido = rfcAEditar,
                onNavigateNext = { rfc ->
                    rfcParaDomicilio = rfc
                    pantallaActual = "registro_domicilio"
                }
            )
            "lista" -> ListaContribuyentesScreen(
                viewModel = viewModel,
                onEditarFisica = { rfc -> rfcAEditar = rfc; pantallaActual = "registro_fisica" },
                onEditarMoral = { rfc -> rfcAEditar = rfc; pantallaActual = "registro_moral" },
                onAgregarDomicilioFisica = { rfc -> rfcParaDomicilio = rfc; pantallaActual = "registro_domicilio" },
                onAgregarDomicilioMoral = { rfc -> rfcParaDomicilio = rfc; pantallaActual = "registro_domicilio" }
            )
            "registro_domicilio" -> RegistroDomicilioFiscalScreen(
                viewModel = viewModel,
                rfcVinculado = rfcParaDomicilio,
                onNavigateMenu = { pantallaActual = "menu" }
            )
        }
    }
}