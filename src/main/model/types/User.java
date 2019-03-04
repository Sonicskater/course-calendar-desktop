package main.model.types;

import main.model.database.DBData;
import main.model.database.DBUniqueID;
import main.model.database.IDTypeMismatchExcception;

public class User extends DBData {

	public User(DBUniqueID id, String type) throws IDTypeMismatchExcception {
		super(id, type);
		// TODO Auto-generated constructor stub
	}

}
