package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.database.*;
import model.types.Course;
import model.types.Department;
import model.types.Program;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("interface/loginView.fxml"));
        primaryStage.setTitle("UWinnipeg Authenticator");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {

        //Init the database connection
        DBProvider.INSTANCE.init(EConnectionStrategies.SQLite);
        DBProvider.INSTANCE.getConnection();

        System.out.println(new DBUniqueID(EDBTypeCode.COURSE));
        Course c = null;
        DBUniqueID id = new DBUniqueID(EDBTypeCode.COURSE);
        id.setNumCode(1);
        try {
            c = new Course(id,new DBUniqueID(EDBTypeCode.DEPARTMENT));
        }catch (Exception e){
            e.printStackTrace();
        }
        c.setCode("SENG");
        c.setNumber(300);

        c.setTitle("Test Course");
        c.setDepartmentID(new DBUniqueID(EDBTypeCode.DEPARTMENT));
        c.setDescription(" This is a course for testing the system.");
        c.addPreReq(new DBUniqueID(EDBTypeCode.COURSE));
        DBUniqueID id2 = new DBUniqueID(EDBTypeCode.COURSE);
        id2.setNumCode(3);
        c.addPreReq(id2);
        c.save();
        try {
            c = DataBase.INSTANCE.getCourse(id);
            System.out.println(c.getTitle());
        }catch (Exception e){
            e.printStackTrace();
        }
        Program p = null;
        try {
            p = new Program(new DBUniqueID(EDBTypeCode.PROGRAM));
        } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
            idTypeMismatchExcception.printStackTrace();
        }
        p.setName("Bachelor of Computer Science");
        p.addRequired(c.getId());
        p.setDescription("A 4-year program that teaches general computer science");
        p.save();
        Department d = null;
        try {
             d = new Department(new DBUniqueID(EDBTypeCode.DEPARTMENT));
        } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
            idTypeMismatchExcception.printStackTrace();
        }
        d.setName("Computer Science");
        d.addProgram(p.getId());
        d.save();

        System.out.println(DataBase.INSTANCE.getCourseIDs());

        System.out.println(c.getPreReqs());

        launch(args);
    }
}