package model.types

import model.database.DBData
import model.database.DBUniqueID
import model.database.EDBTypeCode
import model.database.IDTypeMismatchExcception

class Program @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID) : DBData(id, EDBTypeCode.PROGRAM){
    var programName = ""
}
