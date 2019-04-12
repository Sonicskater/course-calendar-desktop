package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.database.*;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("interface/loginView.fxml")));
        primaryStage.setTitle("UWinnipeg Authenticator");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {

        //Init the database connection
        DBProvider.INSTANCE.init(EConnectionStrategies.SQLite);
        DBProvider.INSTANCE.getConnection();

        System.out.println(new DBUniqueID(EDBTypeCode.COURSE));


        launch(args);
    }
}