package model.types;

import java.util.ArrayList;

import model.database.DBProvider;
import model.database.InitException;

public class Course {
	public ArrayList<Course> prereqs = new ArrayList<Course>();
	public ArrayList<Course> antireqs = new ArrayList<Course>();
	
	private int courseNumber;
	private String departmentCode;
	private String departmentID;
	
	public Department Department() throws DataNotFoundException {
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
