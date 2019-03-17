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
					+ " pass text, \n"
                    + " type text \n"
					+");";
			connection.createStatement().execute(sql);
			sql = "CREATE TABLE IF NOT EXISTS programs (\n"
					+ " id integer PRIMARY KEY, \n"
					+ " name text, \n"
					+ " description text \n"
					+");";
			connection.createStatement().execute(sql);

			sql = "CREATE TABLE IF NOT EXISTS antireqs (\n"
                    + " courseID integer, \n"
                    + " antiID integer, \n"
                    + " PRIMARY KEY (courseID,antiID) \n"
                    + ");";
			connection.createStatement().execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS prereqs (\n"
                    + " courseID integer, \n"
                    + " preID integer, \n"
                    + " PRIMARY KEY (courseID,preID) \n"
                    + ");";
            connection.createStatement().execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS required(\n"
                    + " programID integer, \n"
                    + " reqID integer, \n"
                    + " PRIMARY KEY (programID,reqID) \n"
                    + ");";
            connection.createStatement().execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS optional (\n"
                    + " programID integer, \n"
                    + " optID integer, \n"
                    + " PRIMARY KEY (programID,optID) \n"
                    + ");";
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

			sql = "SELECT antiID FROM antireqs WHERE courseID = " + code.getNumCode();
			rs = connection.createStatement().executeQuery(sql);

			while(rs.next()){
			    DBUniqueID id2 = new DBUniqueID(EDBTypeCode.COURSE);
			    id2.setNumCode(rs.getInt("antiID"));
			    course.addAntiReq(id2);
            }

            sql = "SELECT preID FROM prereqs WHERE courseID = " + code.getNumCode();
            rs = connection.createStatement().executeQuery(sql);

            while(rs.next()){
                DBUniqueID id2 = new DBUniqueID(EDBTypeCode.COURSE);
                id2.setNumCode(rs.getInt("preID"));
                course.addPreReq(id2);
            }

			return course;

		}catch (Exception e){
		    e.printStackTrace();
			return null;
		}
	}

	@Override
	public Program GetProgramFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
        if (code.getTypeCode() != EDBTypeCode.PROGRAM){
            throw new IDTypeMismatchExcception();
        }
        String sql = "SELECT * FROM programs WHERE id = " + code.getNumCode();
        try {
            ResultSet rs = connection.prepareStatement(sql).executeQuery();
            Program program = new Program(code);
            program.setName(rs.getString("name"));
            program.setDescription(rs.getString("description"));

            sql = "SELECT optID FROM optional WHERE programID = " + code.getNumCode();
            rs = connection.createStatement().executeQuery(sql);
            while(rs.next()){
                DBUniqueID id2 = new DBUniqueID(EDBTypeCode.COURSE);
                id2.setNumCode(rs.getInt("optID"));
                program.addOptional(id2);
            }

            sql = "SELECT reqID FROM required WHERE programID = " + code.getNumCode();
            rs = connection.createStatement().executeQuery(sql);
            while(rs.next()){
                DBUniqueID id2 = new DBUniqueID(EDBTypeCode.COURSE);
                id2.setNumCode(rs.getInt("reqID"));
                program.addRequired(id2);
            }

            return program;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public User GetUserFromCode(DBUniqueID code) throws IDTypeMismatchExcception {
        if (code.getTypeCode() != EDBTypeCode.USER){
            throw new IDTypeMismatchExcception();
        }
        String sql = "SELECT * FROM users WHERE id =" + code.getNumCode();
        try {
            ResultSet rs = connection.prepareStatement(sql).executeQuery();
            User user = new User(code);
            user.setPass(rs.getString("pass"));
            user.setType(rs.getString("type"));
            user.setUser(rs.getString("user"));

            return user;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void DeleteFromCode(DBUniqueID code) {
	    try {
            String table = "";
            switch (code.getTypeCode()) {
                case DEPARTMENT:
                    table = "departments";
                    break;
                case COURSE:
                    table = "courses";
                    break;
                case PROGRAM:
                    table = "programs";
                    break;
                case USER:
                    table = "users";
                    break;
            }

            String sql = "DELETE FROM ? WHERE id = ?";
            PreparedStatement prst = connection.prepareStatement(sql);
            prst.setString(1, table);
            prst.setInt(2,code.getNumCode());

            prst.execute();

        }catch (Exception e){
	        e.printStackTrace();
        }
	}

	@Override
	public void SetDepFromCode(DBUniqueID code, Department department) throws IDTypeMismatchExcception {
        try {
            if (code.getTypeCode() != EDBTypeCode.DEPARTMENT){
                throw new IDTypeMismatchExcception();
            }

            String sql = "REPLACE INTO departments(id, name) \n"
                    + "VALUES(?,?);";

            PreparedStatement prst = connection.prepareStatement(sql);
            prst.setInt(1, code.getNumCode());
            prst.setString(2,department.getName());
            prst.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
	public void SetCourseFromCode(DBUniqueID code, Course course) throws IDTypeMismatchExcception {
		try {
            if (code.getTypeCode() != EDBTypeCode.COURSE){
                throw new IDTypeMismatchExcception();
            }
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
			for (DBUniqueID c : course.getPreReqs()){
                sql = "REPLACE INTO prereqs(courseID,preID) \n"
                        + "VALUES (?,?);";
                PreparedStatement prst = connection.prepareStatement(sql);
                prst.setInt(1,course.getId().getNumCode());
                prst.setInt(2,c.getNumCode());
                prst.execute();
            }
            for (DBUniqueID c : course.getAntiReqs()){
                sql = "REPLACE INTO antireqs(courseID,antiID) \n"
                        + "VALUES (?,?);";
                PreparedStatement prst = connection.prepareStatement(sql);
                prst.setInt(1,course.getId().getNumCode());
                prst.setInt(2,c.getNumCode());
                prst.execute();
            }

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void SetProgramFromCode(DBUniqueID code, Program program) throws IDTypeMismatchExcception {
	    try {
            if (code.getTypeCode() != EDBTypeCode.PROGRAM){
                throw new IDTypeMismatchExcception();
            }
            String sql = "REPLACE INTO programs(id,name,description) \n"
                    + "VALUES (?,?,?);";
            PreparedStatement prst = connection.prepareStatement(sql);
            prst.setInt(1,code.getNumCode());
            prst.setString(2,program.getName());
            prst.setString(3,program.getDescription());

            prst.execute();

            for (DBUniqueID c : program.getRequired()){
                sql = "REPLACE INTO required(programID,reqID) \n"
                        + "VALUES (?,?);";
                prst = connection.prepareStatement(sql);
                prst.setInt(1,program.getId().getNumCode());
                prst.setInt(2,c.getNumCode());
                prst.execute();
            }
            for (DBUniqueID c : program.getOptional()){
                sql = "REPLACE INTO optional(programID,optID) \n"
                        + "VALUES (?,?);";
                prst = connection.prepareStatement(sql);
                prst.setInt(1,program.getId().getNumCode());
                prst.setInt(2,c.getNumCode());
                prst.execute();
            }

        }catch (Exception e){
	        e.printStackTrace();
        }
	}

	@Override
	public void SetUserFromCode(DBUniqueID code, User user) throws IDTypeMismatchExcception {
        try{
            if (code.getTypeCode() != EDBTypeCode.USER){
                throw new IDTypeMismatchExcception();
            }
            String sql = "REPLACE INTO users(id,user,pass) \n"
                    + "VALUES (?,?,?);";
            PreparedStatement prst = connection.prepareStatement(sql);
            prst.setInt(1,code.getNumCode());
            prst.setString(2,user.getUser());
            prst.setString(3,user.getPass());

            prst.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
	public ArrayList<DBUniqueID> getAllUserIDs() {
        ArrayList<DBUniqueID> ids = new ArrayList<>();
	    try {
            String sql = "SELECT id FROM users";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while(rs.next()){
                DBUniqueID id = new DBUniqueID(EDBTypeCode.USER);
                id.setNumCode(rs.getInt("id"));
                ids.add(id);
            }

        }catch (Exception e){
	        e.printStackTrace();
        }
	    return ids;
	}

	@Override
	public ArrayList<DBUniqueID> getAllCourseIDs() {
        ArrayList<DBUniqueID> ids = new ArrayList<>();
        try {
            String sql = "SELECT id FROM courses";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while(rs.next()){
                DBUniqueID id = new DBUniqueID(EDBTypeCode.COURSE);
                id.setNumCode(rs.getInt("id"));
                ids.add(id);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ids;
	}

	@Override
	public ArrayList<DBUniqueID> getAllProgramIDs() {
        ArrayList<DBUniqueID> ids = new ArrayList<>();
        try {
            String sql = "SELECT id FROM programs";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while(rs.next()){
                DBUniqueID id = new DBUniqueID(EDBTypeCode.PROGRAM);
                id.setNumCode(rs.getInt("id"));
                ids.add(id);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ids;
	}

	@Override
	public ArrayList<DBUniqueID> getAllDepartmentIDs() {
        ArrayList<DBUniqueID> ids = new ArrayList<>();
        try {
            String sql = "SELECT id FROM departments";
            ResultSet rs = connection.createStatement().executeQuery(sql);

            while(rs.next()){
                DBUniqueID id = new DBUniqueID(EDBTypeCode.DEPARTMENT);
                id.setNumCode(rs.getInt("id"));
                ids.add(id);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ids;
	}


	//Implement methods from interface, as well as any additional helper methods here. Any methods here and not in the interface should be private.
}
