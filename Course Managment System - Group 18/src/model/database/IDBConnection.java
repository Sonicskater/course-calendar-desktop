package model.database;

import model.types.Department;

public interface IDBConnection {
	//Define functions for controllers here
	public Department GetDepFromCode(String depID);
}
