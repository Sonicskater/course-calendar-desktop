package controller;


import model.database.DBUniqueID;
import model.database.DataBase;
import model.database.IDTypeMismatchExcception;
import model.types.Course;
import model.types.Department;
import model.types.Program;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    public List<CombinedData> getData(){

        List<CombinedData> dataOut = new ArrayList<>();

        List<DBUniqueID> departmentIDs = DataBase.INSTANCE.getDepartmentIDs();
        for (DBUniqueID depId : departmentIDs){
            Department dep;
            try {
                dep = DataBase.INSTANCE.getDepartment(depId);
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
                continue;
            }

            List<DBUniqueID> programsIds = dep.getPrograms();

            for (DBUniqueID proId : programsIds ){

                Program program;

                try {
                    program = DataBase.INSTANCE.getProgram(proId);
                } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                    continue;
                }

                List<DBUniqueID> required = program.getRequired();
                for (DBUniqueID courseId : required){
                    Course course;
                    try {
                        course = DataBase.INSTANCE.getCourse(courseId);
                    } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                        idTypeMismatchExcception.printStackTrace();
                        continue;
                    }
                    dataOut.add(new CombinedData(dep,program,course,true));

                }
                List<DBUniqueID> optional = program.getOptional();
                for(DBUniqueID courseId :optional) {
                    Course course;
                    try {
                        course = DataBase.INSTANCE.getCourse(courseId);
                    } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                        continue;
                    }
                    dataOut.add(new CombinedData(dep,program,course,false));
                }

            }

        }



        return dataOut;
    }
}
