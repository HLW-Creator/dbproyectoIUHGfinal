package net_hchg.dbproyecto

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import net_hchg.dbproyecto.navigation.NavGraph
import net_hchg.dbproyecto.viewmodel.ContribuyentesViewModel
import java.io.File

fun main() = application {


    val dbFile = File("contribuyentes.db")
    val url = "jdbc:sqlite:${dbFile.absolutePath}"
    val driver = JdbcSqliteDriver(url)


    if (!dbFile.exists()) {
        AppDatabase.Schema.create(driver)
    }

    val database = AppDatabase(driver)

    val repository = ContribuyentesRepository(database)
    repository.inicializarCatalogos()
    val viewModel = ContribuyentesViewModel(repository)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Sistema de Gestión SAT"
    ) {
        NavGraph(viewModel = viewModel)
    }
}