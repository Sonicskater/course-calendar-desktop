package model.database

import java.util.HashMap

object DBCache {
    private val cache = HashMap<DBUniqueID, DBData>()

    private fun isNotCached(id: DBUniqueID): Boolean {
        return !cache.containsKey(id)
    }

    @Throws(InitException::class)
    fun <T : DBData> GetData(id: DBUniqueID): T {
        if (isNotCached(id)) {
            cache[id] = DBProvider.Instance().GetConnection().GetDataFromCode(id)
        }
        return cache[id] as T
    }
}
