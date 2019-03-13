package model.database;

public class DBProvider {
	private DBProvider() {
		
	}
	private EConnectionStrategies strategy;
	
	//Make provider Singleton.
	private static DBProvider instance;
	static DBProvider Instance() {
		if (instance.equals(null)){
			instance = new DBProvider();
		}
		return instance;
		
	}
	
	//Init Method to choose strategy.
	public void Init(EConnectionStrategies strat) throws InitException {
		if (strategy == EConnectionStrategies.NONE) {
			connection = CreateConnection(strat);
		} else {
			throw new InitException(EInitExceptionCodes.ALREADY_INIT);
		}
			
	}
	
	//Creates the connection object.
	private IDBConnection CreateConnection(EConnectionStrategies strat) throws InitException {
		// TODO Auto-generated method stub
		throw new InitException(EInitExceptionCodes.INIT_FAILURE);
	}
	private IDBConnection connection;
	
	//Gets the connection for other classes.
	public IDBConnection GetConnection() throws InitException {
		if (connection.equals(null)) {
			throw new InitException(EInitExceptionCodes.NOT_INIT);
		} else {
			return connection;
		}
	}
}
