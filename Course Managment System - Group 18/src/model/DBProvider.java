package model;

public class DBProvider {
	private DBProvider() {
		
	}
	private EConnectionStrategies strategy;
	private static DBProvider instance;
	public static DBProvider Instance() {
		if (instance.equals(null)){
			instance = new DBProvider();
		}
		return instance;
		
	}
	public void Init(EConnectionStrategies strat) throws InitException {
		if (strategy == EConnectionStrategies.NONE) {
			connection = CreateConnection(strat);
		} else {
			throw new InitException(EInitExceptionCodes.ALREADY_INIT);
		}
			
	}
	
	private IDBConnection CreateConnection(EConnectionStrategies strat) throws InitException {
		// TODO Auto-generated method stub
		throw new InitException(EInitExceptionCodes.INIT_FAILURE);
	}
	private IDBConnection connection;
	
	public IDBConnection GetConnection() throws InitException {
		if (connection.equals(null)) {
			throw new InitException(EInitExceptionCodes.NOT_INIT);
		} else {
			return connection;
		}
	}
}
