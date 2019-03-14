package model.database

abstract class DBData @Throws(IDTypeMismatchExcception::class)
constructor(id: DBUniqueID, type: String) {
    var id: DBUniqueID? = null

    init {
        if (id.TypeCode !== type) {
            throw IDTypeMismatchExcception()
        }
        this.id = id
    }

}
