package model.database

import model.types.*
import java.util.HashMap


/* ############ DATABASE INSTRUCTIONS ############

- All interactions are done through this DataBase class, or through calling <object>.save() or <object>.delete().

- Objects are only unique by the numerical value of their DBUniqueID, not by any other constraints. To get a new, unique one call getNewID on this class.

- SQLite database must be initialized exactly ONCE by calling DBProvider.INSTANCE.init(EDBConnectionStrategies.SQLITE) (I've added this line to main)

*/



//Object instead of class implements singleton pattern boilerplate at compile time
//use DataBase.INSTANCE instead of new DataBase();
object DataBase {
    private val cache = HashMap<DBUniqueID, DBData>()

    //Extension methods

    //Caching disabled for now, Cant work around without extensive work as java has no value type object equivalent, like a struct.
    //Could be implemented easily in C/C++/C#, or by manually rewriting equals.
    //No real gains to be had at this scale, will leave for now
    private fun DBUniqueID.isNotCached(): Boolean {
        return true
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


    fun getUserIDs() : List<DBUniqueID>{
        return DBProvider.connection.getAllUserIDs()
    }

    fun getProgramIDs() : List<DBUniqueID>{
        return DBProvider.connection.getAllProgramIDs()
    }

    fun getCourseIDs() : List<DBUniqueID>{
        return DBProvider.connection.getAllCourseIDs()
    }

    fun getDepartmentIDs() : List<DBUniqueID>{
        return DBProvider.connection.getAllDepartmentIDs()
    }

    fun deleteFromID(id : DBUniqueID){
        DBProvider.connection.DeleteFromCode(id)
    }

    fun getNewID(type : EDBTypeCode) : DBUniqueID{
        val db = DBUniqueID(type)
        db.NumCode = DBProvider.connection.GetNewKey(type)
        return db
    }

}
