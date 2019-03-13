package model.database

import model.types.*
import java.util.HashMap

object DBCache {
    private val cache = HashMap<DBUniqueID, DBData>()

    private fun isNotCached(id: DBUniqueID): Boolean {
        return !cache.containsKey(id)
    }
    //kotlin doesn't have a throws syntax, so java code is made aware through annotation
	//inlnie fucntion is used to share code between calls while also working around jvm type erasure.
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
	//These are needed to expose above function to java as java can't call inline functions.
	//Allows for sharing of logic while also keeping interface simple (no need to pass special Class objects to cast types correctly).
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
