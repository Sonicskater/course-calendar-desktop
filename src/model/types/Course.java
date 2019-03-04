package model.types;
import java.util.ArrayList;

import model.database.DBData;
import model.database.DBProvider;
import model.database.DBUniqueID;
import model.database.IDTypeMismatchExcception;
import model.database.InitException;

public class Course extends DBData {
	public ArrayList<Course> prereqs = new ArrayList<Course>();
	public ArrayList<Course> antireqs = new ArrayList<Course>();
	
	private int courseNumber;
	private String departmentCode;
	private DBUniqueID departmentID;
	
	public Course(DBUniqueID id) throws IDTypeMismatchExcception {
		super(id,"Course");
	}
	
	public Department Department() throws DataNotFoundException, IDTypeMismatchExcception {
		try {
			return DBProvider.Instance().GetConnection().GetDepFromCode(departmentID);
		} catch (InitException e) {
			throw new DataNotFoundException();
		}
	}
	public String Title() {
		return departmentCode+" "+courseNumber;
	}
}
