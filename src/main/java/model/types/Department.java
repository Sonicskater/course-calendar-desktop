package model.types;

import model.database.DBData;
import model.database.DBUniqueID;
import model.database.IDTypeMismatchExcception;

public class Department extends DBData{

	public Department(DBUniqueID id, String type) throws IDTypeMismatchExcception {
		super(id, type);
		// TODO Auto-generated constructor stub
	}

}
