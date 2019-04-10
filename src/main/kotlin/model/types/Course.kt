package model.types

import model.database.*
import java.util.*

class Course @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID) : DBData(id, EDBTypeCode.COURSE)
{
    var departmentID : DBUniqueID = DBUniqueID(EDBTypeCode.DEPARTMENT)
    var code : String = ""
    var number : Int = 0

    var year : Int = 2000

    private var prereqs = ArrayList<DBUniqueID>()
    private var antireqs = ArrayList<DBUniqueID>()

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

    fun removeAntiReq(id : DBUniqueID) = antireqs.remove(id)

    fun removePreReq(id : DBUniqueID) = prereqs.remove(id)

    fun getName(): String {
        return code
    }
    var title = ""
    var description = ""
}
