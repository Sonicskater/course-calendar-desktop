package model.database

import model.types.*
import java.lang.Exception

abstract class DBData @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID, type: EDBTypeCode) {
    var id: DBUniqueID = DBProvider.connection.InitData(type)

    init {
        if (id.TypeCode !== type) {
            throw IDTypeMismatchExcception()
        }
        this.id = id
    }

    fun save():Boolean{
        try {
            when(this.id.TypeCode){
                EDBTypeCode.COURSE -> DBProvider.connection.SetCourseFromCode(this.id,this as Course)
                EDBTypeCode.DEPARTMENT -> DBProvider.connection.SetDepFromCode(this.id,this as Department)
                EDBTypeCode.USER -> DBProvider.connection.SetUserFromCode(this.id,this as User)
                EDBTypeCode.PROGRAM -> DBProvider.connection.SetProgramFromCode(this.id,this as Program)
            }
        }catch (e :Exception){
            return false
        }
        return true
    }



}
