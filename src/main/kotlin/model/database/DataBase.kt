package model.database

import model.types.*
import java.util.HashMap

//Object instead of class implements singleton pattern boilerplate at compile time
//use DataBase.INSTANCE instead of new DataBase();
object DataBase {
    init {
        DBProvider.init(EConnectionStrategies.SQLite)
    }
    private val cache = HashMap<DBUniqueID, DBData>()

    //Extension methods
    private fun DBUniqueID.isNotCached(): Boolean {
        return !cache.containsKey(this)
    }
    private fun DBUniqueID.isCached(): Boolean{
        return cache.containsKey(this)
    }
    //kotlin doesn't have a throws syntax, so java code is made aware through annotation
	//inline function is used to share code between calls while also working around jvm generic type erasure.
    //inline function copies the below code to any calling point instead of calling function normally (similar to a macro)
    @Throws(InitException::class)
    private inline fun <reified T : DBData> getDataCached(id: DBUniqueID): T {
        if (id.isNotCached()) {
            cache[id] = DBProvider.connection.GetDataFromCode(id)
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
    fun getCourse(id: DBUniqueID) : Course{
        return getDataCached(id)
    }
    @Throws(IDTypeMismatchExcception::class)
    fun getDepartment(id: DBUniqueID) : Department{
        return getDataCached(id)
    }
    @Throws(IDTypeMismatchExcception::class)
    fun getProgram(id: DBUniqueID) : Program{
        return getDataCached(id)
    }
    @Throws(IDTypeMismatchExcception::class)
    fun getUser(id: DBUniqueID) : User{
        return getDataCached(id)
    }
}
