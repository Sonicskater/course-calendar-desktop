package model.database

import model.types.Course
import java.lang.reflect.Type
import kotlin.reflect.KClass

class DBUniqueID(val TypeCode: EDBTypeCode, var NumCode: Int) {



    override fun toString(): String {
        return TypeCode.string + "_" + NumCode
    }
    fun checkType(code : EDBTypeCode) : Boolean{
        return this.TypeCode == code
    }
}
