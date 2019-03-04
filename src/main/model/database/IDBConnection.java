package main.model.database;

import main.model.types.Course;
import main.model.types.Department;
import main.model.types.Program;
import main.model.types.User;

public interface IDBConnection {
	//Define functions for controllers here
	
	//Creates a unique primary key number for the type requested
	public DBUniqueID InitData(String type);
	//Required methods
	DBData GetDataFromCode(DBUniqueID code);
	
	void WriteData(DBData Data);
	
	//Default methods that should be overridden if type-specific functionality is needed.
	//Please overwrite instead of using switch statements.
	public default Department GetDepFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode == "Department") {
			return (Department)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}
	
	public default Course GetCourseFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode == "Course") {
			return (Course)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}
	
	public default Program GetProgramFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode == "Program") {
			return (Program)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}
	
	public default User GetUserFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode == "User") {
			return (User)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}
	

	
}
