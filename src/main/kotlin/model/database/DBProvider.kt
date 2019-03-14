package model.database



object DBProvider{
    lateinit var strategy: EConnectionStrategies private set

    val connection: IDBConnection by lazy { openConnection()}

    fun init(strat : EConnectionStrategies = EConnectionStrategies.SQLite){
        if (this::strategy.isInitialized){
            throw InitException(EInitExceptionCodes.ALREADY_INIT)
        }else{
            this.strategy = strat
        }
    }

    private fun openConnection() : IDBConnection{
        if (this::strategy.isInitialized) {
            return when (strategy) {
                EConnectionStrategies.SQLite -> SQLiteStrategy()
                EConnectionStrategies.NONE -> throw InitException(EInitExceptionCodes.NOT_INIT)
            }
        }else{
            throw InitException(EInitExceptionCodes.NOT_INIT)
        }
    }


}