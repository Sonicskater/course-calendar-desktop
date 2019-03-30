package model.database

//Contains class + primary key of object, needed as java has generic type erasure i need to work around -_- (no checking if object is type T without kotlin trickery)
class DBUniqueID(val TypeCode: EDBTypeCode) {
    var NumCode: Int = DBProvider.connection.GetNewKey(TypeCode)



    override fun toString(): String {
        return TypeCode.string + "_" + NumCode
    }
    fun checkType(code : EDBTypeCode) : Boolean{
        return this.TypeCode == code
    }
}
