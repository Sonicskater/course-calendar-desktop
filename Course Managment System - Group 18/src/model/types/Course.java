package model.types;

import java.util.ArrayList;

public class Course {
	public ArrayList<Course> prereqs = new ArrayList<Course>();
	public ArrayList<Course> antireqs = new ArrayList<Course>();
	
	private int courseNumber;
	private String departmentCode;
	public String Title() {
		return departmentCode+" "+courseNumber;
	}
}
