package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


public class UserController implements Initializable {
    private static final int STUDENT = 0;
    private static final int FACULTY = 1;

    private int userType;
    @FXML private HBox facultyPane;

    @FXML private ToggleGroup actionGroup;
    @FXML private RadioButton departRButton;
    @FXML private RadioButton programRButton;
    @FXML private RadioButton courseRButton;

    @FXML private Label descriptionLabel;
    @FXML private ListView prereqListView = new ListView();
    @FXML private ListView antireqListView = new ListView();

    @FXML private TreeTableView<String> tableView;
    @FXML private TreeTableColumn progCol;
    @FXML private TreeTableColumn<String, String> yearCol;
    @FXML private TreeTableColumn<String, String> codeCol;
    @FXML private TreeTableColumn<String, String> titleCol;

    public void setUserType(int userType) {
        this.userType = userType;
        setUserDisplay(userType);
    }

    public void setUserDisplay(int userType) {
        if (userType == STUDENT)
            facultyPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateSelectedItemInfo();

        actionGroup = new ToggleGroup();
        departRButton.setToggleGroup(actionGroup);
        programRButton.setToggleGroup(actionGroup);
        courseRButton.setToggleGroup(actionGroup);
        actionGroup.selectToggle(departRButton);
    }

    // Change to work when item is selected and retrieve data from DB
    public void updateSelectedItemInfo() {
        prereqListView.getItems().addAll(new ArrayList<>(Arrays.asList("AAAA111", "BBBB222", "CCCC333", "DDDD444", "EEEE555","FFFF666")));
        antireqListView.getItems().addAll(new ArrayList<>(Arrays.asList("XXXX123", "YYYY456", "ZZZZ789")));
        descriptionLabel.setText("Introduction into the development and evolution of software. Covers key conceptual foundations as well as key methods and techniques used in the different phases of the software lifecycle. Technologies are selected based on their fitness for purpose towards explicitly stated project objectives for different types of project. Emphasis is on both technical and soft skill needed for high quality software and software-based products developed in teams.");
    }

    public void logOut(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../../resources/interface/loginView.fxml"));
        Scene newScene = new Scene(parent);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(newScene);
        loginStage.show();
    }

    public void add() {
        if (this.actionGroup.getSelectedToggle().equals(this.departRButton)) {
            addDepartment();
        } else if (this.actionGroup.getSelectedToggle().equals(this.programRButton)) {
            addProgram();
        } else if (this.actionGroup.getSelectedToggle().equals(this.courseRButton)) {
            addCourse();
        }
    }

    public void delete() {
        if (this.actionGroup.getSelectedToggle().equals(this.departRButton)) {
            deleteDepartment();
        } else if (this.actionGroup.getSelectedToggle().equals(this.programRButton)) {
            deleteProgram();
        } else if (this.actionGroup.getSelectedToggle().equals(this.courseRButton)) {
            deleteCourse();
        }
    }

    public void edit() {
        if (this.actionGroup.getSelectedToggle().equals(this.departRButton)) {
            editDepartment();
        } else if (this.actionGroup.getSelectedToggle().equals(this.programRButton)) {
            editProgram();
        } else if (this.actionGroup.getSelectedToggle().equals(this.courseRButton)) {
            editCourse();
        }
    }

    public void addDepartment() {
        System.out.println("Adding Department");
    }

    public void addProgram() {
        System.out.println("Adding Program");
    }

    public void addCourse() {
        System.out.println("Adding Course");
    }

    public void deleteDepartment() {
        System.out.println("Deleting Department");
    }

    public void deleteProgram() {
        System.out.println("Deleting Program");
    }

    public void deleteCourse() {
        System.out.println("Deleting Course");
    }

    public void editDepartment() {
        System.out.println("Editing Department");
    }

    public void editProgram() {
        System.out.println("Editing Program");
    }

    public void editCourse() {
        System.out.println("Editing Course");
    }
}