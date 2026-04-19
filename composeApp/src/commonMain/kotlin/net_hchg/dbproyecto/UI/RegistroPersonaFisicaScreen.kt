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
fun RegistroPersonaFisicaScreen(
    viewModel: ContribuyentesViewModel,
    rfcRecibido: String?,
    onNavigateBack: () -> Unit
) {
    val listaEstados by viewModel.estados.collectAsState()
    val listaMunicipios by viewModel.municipios.collectAsState()
    val estadoActual by viewModel.estadoSeleccionado.collectAsState()
    val municipioActual by viewModel.municipioSeleccionado.collectAsState()

    var rfc by remember { mutableStateOf("") }
    var curp by remember { mutableStateOf("") }
    var nombreCompleto by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var codigoPostal by remember { mutableStateOf("") }
    var tipoVialidad by remember { mutableStateOf("") }
    var actividadEconomica by remember { mutableStateOf("") }
    var regimenFiscal by remember { mutableStateOf("") }

    LaunchedEffect(rfcRecibido) {
        if (rfcRecibido != null) {
            val persona = viewModel.obtenerPersonaFisica(rfcRecibido)
            if (persona != null) {
                rfc = persona.rfc
                curp = persona.curp
                nombreCompleto = persona.nombre_completo
                correo = persona.correo
                telefono = persona.telefono
                fechaNacimiento = persona.fecha_nacimiento
                codigoPostal = persona.codigo_postal
                tipoVialidad = persona.tipo_vialidad
                actividadEconomica = persona.actividad_economica
                regimenFiscal = persona.regimen_fiscal
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
            text = if (rfcRecibido == null) "Registro de Persona Física" else "Modificar Persona Física",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nombreCompleto,
            onValueChange = { nombreCompleto = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = rfc,
            onValueChange = { if (it.length <= 13) rfc = it.uppercase() },
            label = { Text("RFC (13 caracteres)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = rfcRecibido == null
        )

        OutlinedTextField(
            value = curp,
            onValueChange = { if (it.length <= 18) curp = it.uppercase() },
            label = { Text("CURP (18 caracteres)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
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
                    viewModel.guardarPersonaFisica(
                        rfc = rfc,
                        curp = curp,
                        nombreCompleto = nombreCompleto,
                        fechaNacimiento = fechaNacimiento,
                        correo = correo,
                        telefono = telefono,
                        codigoPostal = codigoPostal,
                        tipoVialidad = tipoVialidad,
                        actividadEconomica = actividadEconomica,
                        regimenFiscal = regimenFiscal
                    )
                } else {
                    viewModel.actualizarPersonaFisica(
                        rfc = rfc,
                        nombreCompleto = nombreCompleto,
                        correo = correo,
                        telefono = telefono,
                        codigoPostal = codigoPostal,
                        tipoVialidad = tipoVialidad,
                        actividadEconomica = actividadEconomica,
                        regimenFiscal = regimenFiscal
                    )
                }
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = rfc.length == 13 && curp.length == 18 && municipioActual != null
        ) {
            Text(if (rfcRecibido == null) "Guardar Persona Física" else "Actualizar Persona Física")
        }
    }
}