package net_hchg.dbproyecto.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net_hchg.dbproyecto.viewmodel.ContribuyentesViewModel

@Composable
fun RegistroPersonaMoralScreen(
    viewModel: ContribuyentesViewModel,
    rfcRecibido: String?,
    onNavigateNext: (String) -> Unit
) {
    val listaEstados by viewModel.estados.collectAsState()
    val listaMunicipios by viewModel.municipios.collectAsState()
    val estadoActual by viewModel.estadoSeleccionado.collectAsState()
    val municipioActual by viewModel.municipioSeleccionado.collectAsState()

    var rfc by remember { mutableStateOf("") }
    var razonSocial by remember { mutableStateOf("") }
    var fechaConstitucion by remember { mutableStateOf("") }
    var rfcRepresentante by remember { mutableStateOf("") }
    var rfcSocios by remember { mutableStateOf("") }
    var numeroEscritura by remember { mutableStateOf("") }
    var codigoPostal by remember { mutableStateOf("") }
    var regimenCapital by remember { mutableStateOf("") }
    var actividadEconomica by remember { mutableStateOf("") }

    LaunchedEffect(rfcRecibido) {
        if (rfcRecibido != null) {
            val empresa = viewModel.obtenerPersonaMoral(rfcRecibido)
            if (empresa != null) {
                rfc = empresa.rfc
                razonSocial = empresa.razon_social
                fechaConstitucion = empresa.fecha_constitucion
                rfcRepresentante = empresa.rfc_representante
                rfcSocios = empresa.rfc_socios ?: ""
                numeroEscritura = empresa.numero_escritura
                codigoPostal = empresa.codigo_postal
                regimenCapital = empresa.regimen_capital
                actividadEconomica = empresa.actividad_economica
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = if (rfcRecibido == null) "Registro de Persona Moral" else "Modificar Persona Moral",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = razonSocial,
            onValueChange = { razonSocial = it },
            label = { Text("Razón Social") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = rfc,
            onValueChange = { if (it.length <= 12) rfc = it.uppercase() },
            label = { Text("RFC (12 caracteres)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = rfcRecibido == null
        )

        OutlinedTextField(
            value = fechaConstitucion,
            onValueChange = { fechaConstitucion = it },
            label = { Text("Fecha de Constitución") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = rfcRepresentante,
            onValueChange = { if (it.length <= 13) rfcRepresentante = it.uppercase() },
            label = { Text("RFC Representante Legal") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = rfcSocios,
            onValueChange = { rfcSocios = it.uppercase() },
            label = { Text("RFC de Socios (Opcional)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = numeroEscritura,
            onValueChange = { numeroEscritura = it },
            label = { Text("Número de Escritura") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = codigoPostal,
            onValueChange = { codigoPostal = it },
            label = { Text("Código Postal") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = regimenCapital,
            onValueChange = { regimenCapital = it },
            label = { Text("Régimen de Capital") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = actividadEconomica,
            onValueChange = { actividadEconomica = it },
            label = { Text("Actividad Económica") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Text(
            text = "Domicilio Fiscal",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AppComboBox(
            label = "Estado",
            items = listaEstados,
            selectedItem = estadoActual,
            onItemSelected = { viewModel.seleccionarEstado(it) },
            itemLabel = { it.nombre }
        )

        AppComboBox(
            label = "Municipio",
            items = listaMunicipios,
            selectedItem = municipioActual,
            onItemSelected = { viewModel.seleccionarMunicipio(it) },
            itemLabel = { it.nombre }
        )

        if (estadoActual == null) {
            Text(
                text = "Selecciona un estado para ver los municipios",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (rfcRecibido == null) {
                    viewModel.guardarPersonaMoral(
                        rfc = rfc,
                        razonSocial = razonSocial,
                        fechaConstitucion = fechaConstitucion,
                        rfcRepresentante = rfcRepresentante,
                        rfcSocios = rfcSocios.ifBlank { null },
                        numeroEscritura = numeroEscritura,
                        codigoPostal = codigoPostal,
                        regimenCapital = regimenCapital,
                        actividadEconomica = actividadEconomica
                    )
                } else {
                    viewModel.actualizarPersonaMoral(
                        rfc = rfc,
                        razonSocial = razonSocial,
                        fechaConstitucion = fechaConstitucion,
                        rfcRepresentante = rfcRepresentante,
                        rfcSocios = rfcSocios.ifBlank { null },
                        numeroEscritura = numeroEscritura,
                        codigoPostal = codigoPostal,
                        regimenCapital = regimenCapital,
                        actividadEconomica = actividadEconomica
                    )
                }
                onNavigateNext(rfc)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = rfc.length == 12 && razonSocial.isNotBlank() && municipioActual != null
        ) {
            Text(if (rfcRecibido == null) "Guardar Persona Moral" else "Actualizar Persona Moral")
        }
    }
}
