package model.database;

import model.types.*;

import java.sql.*;
import java.util.ArrayList;

//THIS STAYS FINAL AND NOT PUBLIC
final class SQLiteStrategy implements IDBConnection {
	SQLiteStrategy(){
		try{
			Class.forName("org.sqlite.JDBC");
			String filename = "sqlite.db";

			String url = "jdbc:sqlite:" + filename;
			connection = DriverManager.getConnection(url);
			System.out.println(connection.getMetaData().getDriverName() + "connection established to "+filename);

			String sql = "CREATE TABLE IF NOT EXISTS courses (\n"
					+ " id integer PRIMARY KEY, \n"
					+ " code text,\n"
					+ " number integer, \n"
					+ " title text, \n"
					+ " description text, \n"
					+ " departmentID integer \n"
					+");";
			connection.createStatement().execute(sql);
			sql = "CREATE TABLE IF NOT EXISTS departments (\n"
					+ " id integer PRIMARY KEY, \n"
					+ " name text \n"
					+");";
			connection.createStatement().execute(sql);
			sql = "CREATE TABLE IF NOT EXISTS users (\n"
					+ " id integer PRIMARY KEY, \n"
					+ " user text, \n"
					+ " pass text \n"
					+");";
			connection.createStatement().execute(sql);
			sql = "CREATE TABLE IF NOT EXISTS programs (\n"
					+ " id integer PRIMARY KEY, \n"
					+ " name text, \n"
					+ " description text \n"
					+");";
			connection.createStatement().execute(sql);

		}catch (SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			System.out.println("Driver not found");
		}
	}
	private Connection connection;
	@Override
	public int GetNewKey(EDBTypeCode code) throws SQLException{
		String type = code.getString().toLowerCase()+"s";
		ResultSet rs = connection.createStatement().executeQuery("SELECT IFNULL(MAX(id),0) as id FROM "+type);
		return rs.getInt("id") + 1;
	}

	@Override
	public Department GetDepFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.getTypeCode() != EDBTypeCode.DEPARTMENT){
			throw new IDTypeMismatchExcception();
		}
		String sql = "SELECT id, name FROM departments WHERE id = " + code.getNumCode();
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			Department dep = new Department(code);
			dep.setName(rs.getString("name"));
			return dep;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Course GetCourseFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
		if (code.getTypeCode() != EDBTypeCode.COURSE){
			throw new IDTypeMismatchExcception();
		}
		String sql = "SELECT * FROM courses WHERE id = " + code.getNumCode();
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			Course course = new Course(code, new DBUniqueID(EDBTypeCode.DEPARTMENT));
			course.setTitle(rs.getString("title"));
			course.setDescription(rs.getString("description"));
			DBUniqueID id = new DBUniqueID(EDBTypeCode.DEPARTMENT);
			id.setNumCode(rs.getInt("departmentID"));
			course.setDepartmentID(id);
			course.setCode(rs.getString("code"));

			return course;

		}catch (Exception e){
			return null;
		}
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
	public void DeleteFromCode(DBUniqueID code) {

	}

	@Override
	public void SetDepFromCode(DBUniqueID code, Department department) throws DBExcception {

	}

	@Override
	public void SetCourseFromCode(DBUniqueID code, Course course) throws DBExcception {
		try {
			String sql = "REPLACE INTO courses(id,code,number,title,description,departmentID) \n"
					+ "VALUES (?,?,?,?,?,?);";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, code.getNumCode());
			preparedStatement.setString(2, course.getCode());
			preparedStatement.setInt(3,course.getNumber());
			preparedStatement.setString(4,course.getTitle());
			preparedStatement.setString(5,course.getDescription());
			preparedStatement.setInt(6,course.getDepartmentID().getNumCode());

			preparedStatement.execute();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void SetProgramFromCode(DBUniqueID code, Program program) throws DBExcception {

	}

	@Override
	public void SetUserFromCode(DBUniqueID code, User user) throws DBExcception {

	}

	@Override
	public ArrayList<DBUniqueID> getAllUserIDs() {
		return null;
	}

	@Override
	public ArrayList<DBUniqueID> getAllCourseIDs() {
		return null;
	}

	@Override
	public ArrayList<DBUniqueID> getAllProgramIDs() {
		return null;
	}

	@Override
	public ArrayList<DBUniqueID> getAllDepartmentIDs() {
		return null;
	}


	//Implement methods from interface, as well as any additional helper methods here. Any methods here and not in the interface should be private.
}
