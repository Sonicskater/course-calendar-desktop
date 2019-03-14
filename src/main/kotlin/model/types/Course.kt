package model.types

import model.database.*
import java.util.*

class Course @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID,
            val departmentCode : String,
            val courseNumber : Int,
            val departmentID : DBUniqueID) : DBData(id, EDBTypeCode.COURSE)
{
    private var prereqs = ArrayList<DBUniqueID>()
    private var antireqs = ArrayList<DBUniqueID>()

    @Throws(DataNotFoundException::class, IDTypeMismatchExcception::class)
    fun Department(): Department {
        try {
            return DBProvider.connection.GetDepFromCode(departmentID)
        } catch (e: InitException) {
            throw DataNotFoundException()
        }

    }

    fun addPreReq(id : DBUniqueID){
        if (id.checkType(EDBTypeCode.COURSE) && !(id in prereqs)) {
            prereqs.add(id)
        }
    }
    fun addAntiReq(id : DBUniqueID){
        if (id.checkType(EDBTypeCode.COURSE) && !(id in antireqs)) {
            antireqs.add(id)
        }
    }
    fun getAntiReqs() = Collections.unmodifiableList(antireqs)

    fun getPreReqs() = Collections.unmodifiableList(prereqs)




    fun title(): String {
        return "$departmentCode $courseNumber"
    }
}
