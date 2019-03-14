package model.database

import org.junit.Assert.*

class DBProviderTest {

    @org.junit.Test
    fun init() {
        DBProvider.init(EConnectionStrategies.SQLite)
    }
}