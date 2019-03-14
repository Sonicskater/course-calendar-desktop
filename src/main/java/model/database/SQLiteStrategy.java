package model.database;

import model.types.*;
//THIS STAYS FINAL AND NOT PUBLIC
final class SQLiteStrategy implements IDBConnection {

	@Override
	public <T extends DBData> DBUniqueID InitData() {
		return null;
	}

	@Override
	public DBData GetDataFromCode(DBUniqueID code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void WriteData(DBData Data) {
		// TODO Auto-generated method stub
		
	}


	

	
	//Implement methods from interface, as well as any additional helper methods here. Any methods here and not in the interface should be private.
}
