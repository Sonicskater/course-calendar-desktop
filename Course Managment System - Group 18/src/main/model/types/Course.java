package main.model.types;
import java.util.ArrayList;

import main.model.database.DBData;
import main.model.database.DBProvider;
import main.model.database.DBUniqueID;
import main.model.database.IDTypeMismatchExcception;
import main.model.database.InitException;

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
