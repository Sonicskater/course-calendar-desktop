package model.database;

import model.types.Course;
import model.types.Department;
import model.types.Program;
import model.types.User;

public interface IDBConnection {
	//Define functions for controllers here
	
	//Creates a unique primary key number for the type requested
	DBUniqueID InitData(String type);
	//Required methods
	DBData GetDataFromCode(DBUniqueID code);
	
	void WriteData(DBData Data);
	
	//Default methods that should be overridden if type-specific functionality is needed.
	//Please overwrite instead of using switch statements.
	default Department GetDepFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode == "Department") {
			return (Department)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}
	
	default Course GetCourseFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode == "Course") {
			return (Course)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}
	
	default Program GetProgramFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode == "Program") {
			return (Program)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}
	
	default User GetUserFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode == "User") {
			return (User)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}
	

	
}
