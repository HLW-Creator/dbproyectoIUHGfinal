package net_hchg.dbproyecto.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nethchg.dbproyecto.DomicilioFiscal
import net_hchg.dbproyecto.DomicilioFiscalRepository
import net_hchg.dbproyecto.ContribuyentesRepository
import nethchg.dbproyecto.Estado
import nethchg.dbproyecto.Municipio
import nethchg.dbproyecto.PersonaFisica
import nethchg.dbproyecto.PersonaMoral



class ContribuyentesViewModel(
    private val repository: ContribuyentesRepository,
    private val domicilioRepo: DomicilioFiscalRepository
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Default)

    val estados: StateFlow<List<Estado>> = repository.getEstados()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val personasFisicas: StateFlow<List<PersonaFisica>> = repository.getPersonasFisicas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val personasMorales: StateFlow<List<PersonaMoral>> = repository.getPersonasMorales()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _estadoSeleccionado = MutableStateFlow<Estado?>(null)
    val estadoSeleccionado: StateFlow<Estado?> = _estadoSeleccionado

    private val _municipioSeleccionado = MutableStateFlow<Municipio?>(null)
    val municipioSeleccionado: StateFlow<Municipio?> = _municipioSeleccionado

    private val _municipios = MutableStateFlow<List<Municipio>>(emptyList())
    val municipios: StateFlow<List<Municipio>> = _municipios


    fun seleccionarEstado(estado: Estado) {
        _estadoSeleccionado.value = estado
        _municipioSeleccionado.value = null
        viewModelScope.launch {
            repository.getMunicipiosPorEstado(estado.id_estado).collect { _municipios.value = it }
        }
    }
    fun seleccionarMunicipio(municipio: Municipio) {
        _municipioSeleccionado.value = municipio
    }

    fun guardarPersonaFisica(
        rfc: String, curp: String, nombreCompleto: String, fechaNacimiento: String,
        correo: String, telefono: String, codigoPostal: String, tipoVialidad: String,
        actividadEconomica: String, regimenFiscal: String
    ) {
        val municipioId = _municipioSeleccionado.value?.id_municipio ?: return

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPersonaFisica(
                rfc = rfc, curp = curp, nombreCompleto = nombreCompleto,
                fechaNacimiento = fechaNacimiento, correo = correo,
                telefono = telefono, codigoPostal = codigoPostal,
                idMunicipio = municipioId, tipoVialidad = tipoVialidad,
                actividadEconomica = actividadEconomica, regimenFiscal = regimenFiscal
            )
        }
    }

    fun guardarPersonaMoral(
        rfc: String, razonSocial: String, fechaConstitucion: String,
        rfcRepresentante: String, rfcSocios: String?, numeroEscritura: String, codigoPostal: String,
        regimenCapital: String, actividadEconomica: String
    ) {
        val municipioId = _municipioSeleccionado.value?.id_municipio ?: return

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPersonaMoral(
                rfc = rfc, razonSocial = razonSocial, fechaConstitucion = fechaConstitucion,
                rfcRepresentante = rfcRepresentante, rfcSocios = null,
                numeroEscritura = numeroEscritura, codigoPostal = codigoPostal,
                idMunicipio = municipioId, regimenCapital = regimenCapital,
                actividadEconomica = actividadEconomica
            )
        }
    }
    fun obtenerPersonaFisica(rfc: String) = repository.obtenerPersonaFisica(rfc)
    fun obtenerPersonaMoral(rfc: String) = repository.obtenerPersonaMoral(rfc)

    fun actualizarPersonaFisica(
        rfc: String, nombreCompleto: String, correo: String, telefono: String,
        codigoPostal: String, tipoVialidad: String, actividadEconomica: String, regimenFiscal: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizarPersonaFisica(
                nombreCompleto, correo, telefono, codigoPostal, tipoVialidad, actividadEconomica, regimenFiscal, rfc
            )
        }
    }
    fun actualizarPersonaMoral(
        rfc: String, razonSocial: String, fechaConstitucion: String, rfcRepresentante: String,
        rfcSocios: String?, numeroEscritura: String, codigoPostal: String, regimenCapital: String, actividadEconomica: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizarPersonaMoral(
                razonSocial, fechaConstitucion, rfcRepresentante, rfcSocios, numeroEscritura, codigoPostal, regimenCapital, actividadEconomica, rfc
            )
        }
    }
    fun eliminarPersonaMoral(rfc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarPersonaMoral(rfc)
        }
    }
    fun eliminarPersonaFisica(rfc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarPersonaFisica(rfc)
        }
    }
    fun guardarDomicilioFiscal(domicilio: DomicilioFiscal) {
        CoroutineScope(Dispatchers.IO).launch {
            domicilioRepo.guardarDomicilio(domicilio)
        }
    }

    fun obtenerDomicilio(rfc: String) = domicilioRepo.obtenerDomicilio(rfc)

}