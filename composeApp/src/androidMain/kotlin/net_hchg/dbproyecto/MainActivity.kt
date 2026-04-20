package net_hchg.dbproyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import net_hchg.dbproyecto.navigation.NavGraph
import net_hchg.dbproyecto.viewmodel.ContribuyentesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val driver = AndroidSqliteDriver(AppDatabase.Schema, applicationContext, "contribuyentes.db")
        val database = AppDatabase(driver)


        val repository = ContribuyentesRepository(database)
        repository.inicializarCatalogos()
        val domicilioRepo = DomicilioFiscalRepository(database)
        val viewModel = ContribuyentesViewModel(repository, domicilioRepo)
        setContent {

            NavGraph(viewModel = viewModel)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}