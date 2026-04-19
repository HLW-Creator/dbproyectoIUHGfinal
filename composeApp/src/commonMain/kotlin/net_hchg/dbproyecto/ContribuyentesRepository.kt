package net_hchg.dbproyecto

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import nethchg.dbproyecto.Estado
import nethchg.dbproyecto.Municipio
import nethchg.dbproyecto.PersonaFisica
import nethchg.dbproyecto.PersonaMoral


class ContribuyentesRepository(private val database: AppDatabase) {

    private val queries = database.appDatabaseQueries


    fun getEstados(): Flow<List<Estado>> {
        return queries.selectAllEstados().asFlow().mapToList(Dispatchers.IO)
    }

    fun getMunicipiosPorEstado(idEstado: Long): Flow<List<Municipio>> {
        return queries.selectMunicipiosByEstado(idEstado).asFlow().mapToList(Dispatchers.IO)
    }
    fun getPersonasFisicas(): Flow<List<PersonaFisica>> {
        return queries.selectAllPersonasFisicas().asFlow().mapToList(Dispatchers.IO)
    }

    fun getPersonasMorales(): Flow<List<PersonaMoral>> {
        return queries.selectAllPersonasMorales().asFlow().mapToList(Dispatchers.IO)
    }

    fun insertPersonaFisica(
        rfc: String, curp: String, nombreCompleto: String, fechaNacimiento: String,
        correo: String, telefono: String, codigoPostal: String, idMunicipio: Long,
        tipoVialidad: String, actividadEconomica: String, regimenFiscal: String
    ) {

        queries.insertPersonaFisica(
            rfc, curp, nombreCompleto, fechaNacimiento, correo,
            telefono, codigoPostal, idMunicipio, tipoVialidad,
            actividadEconomica, regimenFiscal
        )
    }

    fun insertPersonaMoral(
        rfc: String, razonSocial: String, fechaConstitucion: String,
        rfcRepresentante: String, rfcSocios: String?, numeroEscritura: String,
        codigoPostal: String, idMunicipio: Long, regimenCapital: String,
        actividadEconomica: String
    ) {
        queries.insertPersonaMoral(
            rfc, razonSocial, fechaConstitucion, rfcRepresentante,
            rfcSocios, numeroEscritura, codigoPostal, idMunicipio,
            regimenCapital, actividadEconomica
        )
    }
    fun inicializarCatalogos() {
        val totalEstados = queries.countEstados().executeAsOne()
        if (totalEstados == 0L) {
            queries.transaction {
                queries.insertEstado(1, "Aguascalientes")
                queries.insertEstado(2, "Baja California")
                queries.insertEstado(3, "Baja California Sur")
                queries.insertEstado(4, "Campeche")
                queries.insertEstado(5, "Coahuila")
                queries.insertEstado(6, "Colima")
                queries.insertEstado(7, "Chiapas")
                queries.insertEstado(8, "Chihuahua")
                queries.insertEstado(9, "Ciudad de México")
                queries.insertEstado(10, "Durango")
                queries.insertEstado(11, "Guanajuato")
                queries.insertEstado(12, "Guerrero")
                queries.insertEstado(13, "Hidalgo")
                queries.insertEstado(14, "Jalisco")
                queries.insertEstado(15, "México")
                queries.insertEstado(16, "Michoacán")
                queries.insertEstado(17, "Morelos")
                queries.insertEstado(18, "Nayarit")
                queries.insertEstado(19, "Nuevo León")
                queries.insertEstado(20, "Oaxaca")
                queries.insertEstado(21, "Puebla")
                queries.insertEstado(22, "Querétaro")
                queries.insertEstado(23, "Quintana Roo")
                queries.insertEstado(24, "San Luis Potosí")
                queries.insertEstado(25, "Sinaloa")
                queries.insertEstado(26, "Sonora")
                queries.insertEstado(27, "Tabasco")
                queries.insertEstado(28, "Tamaulipas")
                queries.insertEstado(29, "Tlaxcala")
                queries.insertEstado(30, "Veracruz")
                queries.insertEstado(31, "Yucatán")
                queries.insertEstado(32, "Zacatecas")
                queries.insertMunicipio(1, 9, "Coyoacán")
                queries.insertMunicipio(2, 9, "Tlalpan")
                queries.insertMunicipio(3, 14, "Guadalajara")
                queries.insertMunicipio(4, 14, "Zapopan")
                queries.insertMunicipio(5, 11, "León")
                queries.insertMunicipio(6, 11, "San Miguel de Allende")
                queries.insertMunicipio(7, 11, "Irapuato")
                queries.insertMunicipio(8, 11, "Celaya")
                queries.insertMunicipio(9, 11, "Guanajuato")
                queries.insertMunicipio(10, 11, "Uriangato")
                queries.insertMunicipio(11, 11, "Silao")
                queries.insertMunicipio(12, 16, "Morelia")
                queries.insertMunicipio(13, 16, "Uruapan")
                queries.insertMunicipio(14, 16, "Zamora")
                queries.insertMunicipio(15, 16, "Pátzcuaro")
                queries.insertMunicipio(16, 16, "Lázaro Cárdenas")
            }
        }

        }
    fun obtenerPersonaFisica(rfc: String): PersonaFisica? {
        return queries.selectPersonaFisicaByRfc(rfc).executeAsOneOrNull()
    }

    fun obtenerPersonaMoral(rfc: String): PersonaMoral? {
        return queries.selectPersonaMoralByRfc(rfc).executeAsOneOrNull()
    }

    fun actualizarPersonaFisica(
        nombreCompleto: String, correo: String, telefono: String,
        codigoPostal: String, tipoVialidad: String,
        actividadEconomica: String, regimenFiscal: String, rfc: String
    ) {
        queries.updatePersonaFisica(
            nombre_completo = nombreCompleto, correo = correo, telefono = telefono,
            codigo_postal = codigoPostal, tipo_vialidad = tipoVialidad,
            actividad_economica = actividadEconomica, regimen_fiscal = regimenFiscal,
            rfc = rfc
        )
    }
    fun actualizarPersonaMoral(
        razonSocial: String, fechaConstitucion: String, rfcRepresentante: String,
        rfcSocios: String?, numeroEscritura: String, codigoPostal: String,
        regimenCapital: String, actividadEconomica: String, rfc: String
    ) {
        queries.updatePersonaMoral(
            razon_social = razonSocial, fecha_constitucion = fechaConstitucion,
            rfc_representante = rfcRepresentante, rfc_socios = rfcSocios,
            numero_escritura = numeroEscritura, codigo_postal = codigoPostal,
            regimen_capital = regimenCapital, actividad_economica = actividadEconomica,
            rfc = rfc
        )
    }
    fun eliminarPersonaMoral(rfc: String) {
        queries.deletePersonaMoral(rfc)
    }
    fun eliminarPersonaFisica(rfc: String) {
        queries.deletePersonaFisica(rfc)
    }
    }
