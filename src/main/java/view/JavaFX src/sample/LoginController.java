package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    static final int STUDENT = 0;
    static final int FACULTY = 1;

    @FXML private TextField userField;
    @FXML private PasswordField passField;
    private boolean authenticated = false;

    @FXML private ToggleGroup userTypeGroup;
    @FXML private RadioButton studentRButton;
    @FXML private RadioButton facultyRButton;

    @FXML private Label badLogInLabel;

    private int userType;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // User Type toggle selection
        userTypeGroup = new ToggleGroup();
        studentRButton.setToggleGroup(userTypeGroup);
        facultyRButton.setToggleGroup(userTypeGroup);
        // Student toggled as default
        userTypeGroup.selectToggle(studentRButton);

        badLogInLabel.setVisible(false);
    }

    public void logIn(ActionEvent event) throws IOException {
        if (this.userTypeGroup.getSelectedToggle().equals(this.studentRButton)) {
            System.out.println("STUDENT LOGIN ATTEMPT (USER:" + userField.getText() + ", PASS:" + passField.getText() + ")");

            this.userType = STUDENT;

            // Call faculty authentication method here
            if (!userField.getText().equals(" ") || !passField.getText().equals("")) {
                badLogInLabel.setVisible(true); // Display log in error message
            } else {
                authenticated = true;
            }
        }

        if (this.userTypeGroup.getSelectedToggle().equals(this.facultyRButton)) {
            System.out.println("FACULTY LOGIN ATTEMPT (USER: " + userField.getText() + ", PASS:" + passField.getText() + ")");

            this.userType = FACULTY;

            // Call faculty authentication method here
            if (!userField.getText().equals(" ") || !passField.getText().equals("")) {
                badLogInLabel.setVisible(true); // Display log in error message
            } else {
                authenticated = true;
            }
        }

        if (authenticated) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("userView.fxml"));
            Parent parent = loader.load();
            Scene newScene = new Scene(parent);
            UserController control = loader.getController();
            control.setUserType(userType); // Pass data into userView
            Stage studentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            studentStage.setTitle("Uwinnipeg Course Manager");
            studentStage.setScene(newScene);
            studentStage.show();
        }
    }
}
