package main.model.database;

import main.model.types.Department;

public interface IDBConnection {
	//Define functions for controllers here
	public Department GetDepFromCode(String depID);
}
