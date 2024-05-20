package data.local

import com.db.Search_historyQueries
import com.db.search_history.search_history
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

abstract class SearchHistoryDatabase {
    companion object {
        fun getDatabaseDao(): Search_historyQueries {
            // Creating a SQLite driver
            val driver = JdbcSqliteDriver("jdbc:sqlite:com.sqldelight.BpSearch_history")
            // Executing SQL statement to create the Bp table if it doesn't exist
            driver.execute(
                null, "CREATE TABLE IF NOT EXISTS SearchHistory (\n" +
                        " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        " city TEXT NOT NULL,\n" +
                        " temp REAL,\n" +
                        " event_time TEXT\n" +
                        ");", 0
            )
            // Create an instance of the database
            val database = search_history(driver)
            // Getting the BpDatabaseQueries object associated with the database
            val myDatabaseQueries = database.search_historyQueries
            // Returning the BpDatabaseQueries object
            return myDatabaseQueries
        }
    }
}