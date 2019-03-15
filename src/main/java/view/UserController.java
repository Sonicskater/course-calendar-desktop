package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UserController implements Initializable {
    static final int STUDENT = 0;
    static final int FACULTY = 1;

    @FXML private Label studentLabel;
    @FXML private Label facultyLabel;

    private int userType;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    public void setUserType(int userType) {
        if (userType == STUDENT) {
            facultyLabel.setText("");
            System.out.println("RUNNING STUDENT VIEW");
        }
        if (userType == FACULTY) {
            studentLabel.setText("");
            System.out.println("RUNNING FACULTY VIEW");
        }

        this.userType = userType;
    }
}