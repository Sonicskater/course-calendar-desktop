package model.database;

import model.types.Course;
import model.types.Department;
import model.types.Program;
import model.types.User;

import java.util.ArrayList;

public interface IDBConnection {
	//Define functions for controllers here

	default DBData GetDataFromCode(DBUniqueID id) throws IDTypeMismatchExcception{
		switch (id.getTypeCode()){
			case COURSE: return GetCourseFromCode(id);
			case DEPARTMENT: return GetDepFromCode(id);
			case USER: return GetUserFromCode(id);
			case PROGRAM: return GetProgramFromCode(id);
			default: throw new IDTypeMismatchExcception();
		}
	}

	//Creates a unique primary key number for the type requested
	DBUniqueID InitData(EDBTypeCode code);

	//Default methods that should be overridden if type-specific functionality is needed.
	//Please overwrite instead of using switch statements.
	Department GetDepFromCode(DBUniqueID code) throws IDTypeMismatchExcception;

	Course GetCourseFromCode(DBUniqueID code) throws IDTypeMismatchExcception;

	Program GetProgramFromCode(DBUniqueID code) throws IDTypeMismatchExcception;

	User GetUserFromCode(DBUniqueID code) throws IDTypeMismatchExcception;

	void SetDepFromCode(DBUniqueID code, Department department) throws DBExcception;

	void SetCourseFromCode(DBUniqueID code, Course course) throws DBExcception;

	void SetProgramFromCode(DBUniqueID code, Program program) throws DBExcception;

	void SetUserFromCode(DBUniqueID code, User user) throws DBExcception;

	ArrayList<DBUniqueID> getAllUserIDs();
	ArrayList<DBUniqueID> getAllCourseIDs();
	ArrayList<DBUniqueID> getAllProgramIDs();
	ArrayList<DBUniqueID> getAllDepartmentIDs();



}
