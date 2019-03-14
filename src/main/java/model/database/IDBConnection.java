package model.database;

import model.types.Course;
import model.types.Department;
import model.types.Program;
import model.types.User;

import java.lang.reflect.Type;

public interface IDBConnection {
	//Define functions for controllers here

	//Creates a unique primary key number for the type requested
	<T extends DBData> DBUniqueID InitData();
	//Required methods
	DBData GetDataFromCode(DBUniqueID code);

	void WriteData(DBData Data);

	//Default methods that should be overridden if type-specific functionality is needed.
	//Please overwrite instead of using switch statements.
	default Department GetDepFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode.equals("Department")) {
			return (Department)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}

	default Course GetCourseFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode.equals("Course")) {
			return (Course)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}

	default Program GetProgramFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode.equals("Program")) {
			return (Program)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}

	default User GetUserFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.TypeCode.equals("User")) {
			return (User)GetDataFromCode(code);
		}else {
			throw new IDTypeMismatchExcception();
		}
	}



}
