package model.types

import model.database.DBData
import model.database.DBUniqueID
import model.database.EDBTypeCode
import model.database.IDTypeMismatchExcception
import java.util.*

class Program @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID) : DBData(id, EDBTypeCode.PROGRAM){
    var name = ""

    var description = ""

    private var required = ArrayList<DBUniqueID>()
    private var optional = ArrayList<DBUniqueID>()

    fun addRequired(id : DBUniqueID){
        if (id.checkType(EDBTypeCode.COURSE) && id !in required && id !in optional) {
            required.add(id)
        }
    }
    fun addOptional(id : DBUniqueID){
        if (id.checkType(EDBTypeCode.COURSE) && id !in optional && id !in required) {
            optional.add(id)
        }
    }
    fun getRequired() = Collections.unmodifiableList(required)

    fun getOptional() = Collections.unmodifiableList(optional)

    fun removeRequired(id : DBUniqueID) = optional.remove(id)

    fun removeOptional(id : DBUniqueID) = required.remove(id)
}
