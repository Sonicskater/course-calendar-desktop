package model.database;

import model.types.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//THIS STAYS FINAL AND NOT PUBLIC
final class SQLiteStrategy implements IDBConnection {
	SQLiteStrategy(){
		try{
			String url = "test.db";
			connection = DriverManager.getConnection(url);
		}catch (SQLException e){
			System.exit(2);
		}
	}
	private static Connection connection;
	@Override
	public DBUniqueID InitData(EDBTypeCode code) {
		return null;
	}

	@Override
	public Department GetDepFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		return null;
	}

	@Override
	public Course GetCourseFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		return null;
	}

	@Override
	public Program GetProgramFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		return null;
	}

	@Override
	public User GetUserFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		return null;
	}

	@Override
	public void SetDepFromCode(DBUniqueID code, Department department) throws DBExcception {

	}

	@Override
	public void SetCourseFromCode(DBUniqueID code, Course course) throws DBExcception {

	}

	@Override
	public void SetProgramFromCode(DBUniqueID code, Program program) throws DBExcception {

	}

	@Override
	public void SetUserFromCode(DBUniqueID code, User user) throws DBExcception {

	}


	//Implement methods from interface, as well as any additional helper methods here. Any methods here and not in the interface should be private.
}
