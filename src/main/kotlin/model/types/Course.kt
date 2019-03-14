package model.types

import model.database.*
import java.util.ArrayList

class Course @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID) : DBData(id, "Course") {
    var prereqs = ArrayList<Course>()
    var antireqs = ArrayList<Course>()

    private val courseNumber: Int = 0
    private val departmentCode: String? = null
    private val departmentID: DBUniqueID? = null

    @Throws(DataNotFoundException::class, IDTypeMismatchExcception::class)
    fun Department(): Department {
        try {
            return DBProvider.connection.GetDepFromCode(departmentID)
        } catch (e: InitException) {
            throw DataNotFoundException()
        }

    }

    fun Title(): String {
        return "$departmentCode $courseNumber"
    }
}
