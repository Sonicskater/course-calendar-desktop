package controller;

import model.types.Course;
import model.types.Department;
import model.types.Program;

public class CombinedData {

    public Department department;
    public Program program;
    public Course course;
    private Boolean required_internal;

    public String getDepartmentName(){
        return department.getName();
    }

    public String getProgramName(){
        return program.getName();
    }

    public String getCourseName(){
        return course.getTitle();
    }

    public String getCourseCode(){
        return course.getName();
    }

    public String getCourseYear(){
        return Integer.toString(course.getYear());
    }

    public String getRequired(){
        return (required_internal) ? "Yes" : "No";
    }

    CombinedData(Department dep, Program prog, Course course, Boolean required){
        this.department = dep;
        this.program = prog;
        this.course = course;
        this.required_internal = required;
    }


}
