package model.types

import model.database.DBData
import model.database.DBUniqueID
import model.database.EDBTypeCode
import model.database.IDTypeMismatchExcception

class User @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID)// TODO Auto-generated constructor stub
    : DBData(id, EDBTypeCode.USER){
    var user = ""
    var pass = ""
}
