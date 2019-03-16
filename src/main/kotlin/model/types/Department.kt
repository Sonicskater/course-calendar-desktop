package model.types

import model.database.DBData
import model.database.DBUniqueID
import model.database.EDBTypeCode
import model.database.IDTypeMismatchExcception
import java.util.*

class Department @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID) : DBData(id, EDBTypeCode.DEP) {

    var name = ""

    private var programs = ArrayList<DBUniqueID>()

    fun addProgram(id : DBUniqueID){
        if (id.checkType(EDBTypeCode.PROGRAM) && !(id in programs)) {
            programs.add(id)
        }
    }

    fun getPrograms() = Collections.unmodifiableList(programs)

    fun removeProgram(id : DBUniqueID) = programs.remove(id)
}
