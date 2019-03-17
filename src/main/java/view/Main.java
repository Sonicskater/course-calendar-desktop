package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.database.*;
import model.types.Course;

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
        System.out.println(DataBase.INSTANCE.getCourseIDs());

        System.out.println(c.getPreReqs());

        launch(args);
    }
}