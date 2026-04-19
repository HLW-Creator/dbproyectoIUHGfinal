package net_hchg.dbproyecto

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import net_hchg.dbproyecto.AppDatabase


actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {

        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:contribuyentes.db")
        AppDatabase.Schema.create(driver)
        return driver
    }
}