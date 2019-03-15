package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("interface/loginView.fxml"));
        primaryStage.setTitle("uwinnipeg Authenticator");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}