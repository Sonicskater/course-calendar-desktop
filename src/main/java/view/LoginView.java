package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginView implements Initializable {
    private static final int STUDENT = 0;
    private static final int FACULTY = 1;

    private int userType = -1; // Type of user, either STUDENT or FACULTY
    private String userTypeTitle; // Title of window based on user type
    @FXML private AnchorPane root;
    // Username and password fields
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    private boolean authenticated = false; // If authenticated as selected user type, evaluates to true

    // Group of radio buttons for selecting the user type
    @FXML private ToggleGroup userTypeGroup;
    @FXML private RadioButton studentRButton;
    @FXML private RadioButton facultyRButton;

    @FXML private Label badLogInLabel; // Message to indicate login failed

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // User Type toggle selection
        userTypeGroup = new ToggleGroup();
        studentRButton.setToggleGroup(userTypeGroup);
        facultyRButton.setToggleGroup(userTypeGroup);
        // Student toggled as default
        userTypeGroup.selectToggle(facultyRButton);

        badLogInLabel.setVisible(false); // Don't show login failed message on start

        //Apply 3rd-party theme
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
    }

    // Runs when login button is clicked
    public void logIn(ActionEvent event) throws IOException {
        // If student radio button selected
        if (this.userTypeGroup.getSelectedToggle().equals(this.studentRButton)) {
            this.userType = STUDENT;
            this.userTypeTitle = "UWinnipeg Course Lister (Student)";

            // Authenticates password
            if (!userField.getText().equals("student") || !passField.getText().equals("pass")) {
                badLogInLabel.setVisible(true); // Display log in error message
            } else {
                authenticated = true;
            }
        }
        // If faculty radio button selected
        if (this.userTypeGroup.getSelectedToggle().equals(this.facultyRButton)) {
            this.userType = FACULTY;
            this.userTypeTitle = "UWinnipeg Course Manager (Faculty)";

            // Authenticates password
            if (!userField.getText().equals("faculty") || !passField.getText().equals("pass")) {
                badLogInLabel.setVisible(true); // Display log in error message
            } else {
                authenticated = true;
            }
        }

        // If password is entered correctly, transition to the new window
        if (authenticated) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("interface/userView.fxml"));
            Parent parent = loader.load();
            Scene newScene = new Scene(parent);
            UserView control = loader.getController();
            control.setUserType(userType); // Pass data into userView
            Stage userStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            userStage.setTitle(this.userTypeTitle);
            userStage.setScene(newScene);
            userStage.show();
        }
    }
}
