package model.database

import org.junit.Test

import org.junit.Assert.*

class DBProviderTest {

    @Test
    fun init() {
        DBProvider.init(EConnectionStrategies.SQLite)
        assertTrue(DBProvider.strategy == EConnectionStrategies.SQLite)
    }
}