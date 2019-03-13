package model.database

import model.types.*
import java.util.HashMap

object DBCache {
    private val cache = HashMap<DBUniqueID, DBData>()

    private fun isNotCached(id: DBUniqueID): Boolean {
        return !cache.containsKey(id)
    }

    @Throws(InitException::class)
    private inline fun <reified T : DBData> GetData(id: DBUniqueID): T {
        if (isNotCached(id)) {
            cache[id] = DBProvider.Instance().GetConnection().GetDataFromCode(id)
        }
        val data = cache[id]
        if (data is T){
            return cache[id] as T
        }
        else{
            throw IDTypeMismatchExcception()
        }

    }
    @Throws(IDTypeMismatchExcception::class)
    public fun GetCourse(id: DBUniqueID) : Course{
        return GetData(id)
    }
    @Throws(IDTypeMismatchExcception::class)
    fun GetDepartment(id: DBUniqueID) : Department{
        return GetData(id)
    }
    @Throws(IDTypeMismatchExcception::class)
    fun GetProgram(id: DBUniqueID) : Program{
        return GetData(id)
    }
    @Throws(IDTypeMismatchExcception::class)
    fun GetUser(id: DBUniqueID) : User{
        return GetData(id)
    }
}
