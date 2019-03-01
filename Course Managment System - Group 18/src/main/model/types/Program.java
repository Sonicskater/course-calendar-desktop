package main.model.types;

import main.model.database.DBData;
import main.model.database.DBUniqueID;
import main.model.database.IDTypeMismatchExcception;

public class Program extends DBData{

	public Program(DBUniqueID id, String type) throws IDTypeMismatchExcception {
		super(id, type);
		// TODO Auto-generated constructor stub
	}

}
