package net_hchg.dbproyecto

import nethchg.dbproyecto.DomicilioFiscal
import net_hchg.dbproyecto.AppDatabase

class DomicilioFiscalRepository(db: AppDatabase) {
    private val queries = db.appDatabaseQueries

    fun guardarDomicilio(domicilio: DomicilioFiscal) {
        queries.insertOrReplaceDomicilioFiscal(
            domicilio.rfc_padre, domicilio.codigo_postal, domicilio.localidad, domicilio.colonia,
            domicilio.tipo_vialidad, domicilio.nombre_vialidad, domicilio.numero_exterior,
            domicilio.numero_interior, domicilio.entre_calle_1, domicilio.entre_calle_2,
            domicilio.referencia_adicional, domicilio.caracteristicas_domicilio
        )
    }

    fun obtenerDomicilio(rfc: String) = queries.selectDomicilioByRfc(rfc).executeAsOneOrNull()
}