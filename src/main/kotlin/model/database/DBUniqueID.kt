package model.database

class DBUniqueID(val TypeCode: EDBTypeCode) {
    var NumCode: Int = DBProvider.connection.GetNewKey(TypeCode)


    override fun toString(): String {
        return TypeCode.string + "_" + NumCode
    }
    fun checkType(code : EDBTypeCode) : Boolean{
        return this.TypeCode == code
    }
}
