package model.types

import model.database.DBData
import model.database.DBUniqueID
import model.database.IDTypeMismatchExcception

class Department @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID, type: String)// TODO Auto-generated constructor stub
    : DBData(id, type)
