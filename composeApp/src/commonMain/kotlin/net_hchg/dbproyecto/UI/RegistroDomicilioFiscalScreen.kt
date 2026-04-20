package net_hchg.dbproyecto.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net_hchg.dbproyecto.viewmodel.ContribuyentesViewModel
import nethchg.dbproyecto.DomicilioFiscal

@Composable
fun RegistroDomicilioFiscalScreen(
    viewModel: ContribuyentesViewModel,
    rfcVinculado: String,
    onNavigateMenu: () -> Unit
) {
    var codigoPostal by remember { mutableStateOf("") }
    var localidad by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var tipoVialidad by remember { mutableStateOf("") }
    var nombreVialidad by remember { mutableStateOf("") }
    var numExt by remember { mutableStateOf("") }
    var numInt by remember { mutableStateOf("") }
    var calle1 by remember { mutableStateOf("") }
    var calle2 by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var caracteristicas by remember { mutableStateOf("") }

    LaunchedEffect(rfcVinculado) {
        val domicilioExistente = viewModel.obtenerDomicilio(rfcVinculado)
        if (domicilioExistente != null) {
            codigoPostal = domicilioExistente.codigo_postal
            localidad = domicilioExistente.localidad
            colonia = domicilioExistente.colonia
            tipoVialidad = domicilioExistente.tipo_vialidad
            nombreVialidad = domicilioExistente.nombre_vialidad
            numExt = domicilioExistente.numero_exterior
            numInt = domicilioExistente.numero_interior ?: ""
            calle1 = domicilioExistente.entre_calle_1
            calle2 = domicilioExistente.entre_calle_2
            referencia = domicilioExistente.referencia_adicional
            caracteristicas = domicilioExistente.caracteristicas_domicilio
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Domicilio Fiscal: $rfcVinculado", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = codigoPostal, onValueChange = { codigoPostal = it }, label = { Text("Código Postal") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = localidad, onValueChange = { localidad = it }, label = { Text("Localidad") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = colonia, onValueChange = { colonia = it }, label = { Text("Colonia") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tipoVialidad, onValueChange = { tipoVialidad = it }, label = { Text("Tipo Vialidad") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = nombreVialidad, onValueChange = { nombreVialidad = it }, label = { Text("Nombre Vialidad") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = numExt, onValueChange = { numExt = it }, label = { Text("Núm Exterior") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = numInt, onValueChange = { numInt = it }, label = { Text("Núm Interior (Opcional)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = calle1, onValueChange = { calle1 = it }, label = { Text("Entre Calle 1") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = calle2, onValueChange = { calle2 = it }, label = { Text("Entre Calle 2") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = referencia, onValueChange = { referencia = it }, label = { Text("Referencia") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = caracteristicas, onValueChange = { caracteristicas = it }, label = { Text("Características") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                viewModel.guardarDomicilioFiscal(DomicilioFiscal(
                    rfcVinculado, codigoPostal, localidad, colonia,
                    tipoVialidad, nombreVialidad, numExt, numInt.ifBlank { null },
                    calle1, calle2, referencia, caracteristicas
                ))
                onNavigateMenu()
            },
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
        ) {
            Text("Guardar Domicilio y Finalizar")
        }
    }
}