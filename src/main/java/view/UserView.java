package view;

import controller.CombinedData;
import controller.MainController;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import javafx.stage.StageStyle;
import javafx.util.Callback;
import jfxtras.styles.jmetro8.JMetro;
import model.database.DBData;
import model.database.DataBase;
import model.database.EDBTypeCode;
import model.types.Course;
import model.types.Program;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UserView implements Initializable {
    private static final int STUDENT = 0;
    private static final int FACULTY = 1;

    private int userType; // Type of user, either STUDENT or FACULTY
    @FXML private AnchorPane root;
    @FXML private HBox facultyPane; // Pane with faculty actions
/*
    // Group of radio buttons for faculty actions
    @FXML private ToggleGroup actionGroup;
    @FXML private RadioButton departRButton;
    @FXML private RadioButton programRButton;
    @FXML private RadioButton courseRButton;
*/
    // Sidebar information
    @FXML private Label descriptionLabel;
    @FXML private ListView prereqListView = new ListView();
    @FXML private ListView antireqListView = new ListView();

    @FXML private TableView<CombinedData> table = new TableView<>();
    @FXML private TableColumn<String, CombinedData> departmentCol;
    @FXML private TableColumn<String, CombinedData> programCol;
    @FXML private TableColumn<String, CombinedData> courseCol;
    @FXML private TableColumn<String, CombinedData> codeCol;
    @FXML private TableColumn<String, CombinedData> requiredCol;
    @FXML private TableColumn<String, CombinedData> yearCol;
/*
    // Tree information
    @FXML private TreeTableView<Program> tableView;
    @FXML private TreeTableColumn<Program, String> progCol;
    @FXML private TreeTableColumn<String, String> yearCol;
    @FXML private TreeTableColumn<String, String> codeCol;
    @FXML private TreeTableColumn<String, String> titleCol;
*/
    private ArrayObservableList<CombinedData> tableList = new ArrayObservableList<>();
    // Used by LoginView to set the user type
    public void setUserType(int userType) {
        this.userType = userType;
        setUserDisplay(userType);
    }

    // Displays facultyPane if faculty, hides otherwise
    public void setUserDisplay(int userType) {
        if (userType == STUDENT)
            facultyPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
/*        updateSelectedItemInfo(); // updates sidebar

        // Group of radio buttons for faculty actions
        actionGroup = new ToggleGroup();
        departRButton.setToggleGroup(actionGroup);
        programRButton.setToggleGroup(actionGroup);
        courseRButton.setToggleGroup(actionGroup);
        actionGroup.selectToggle(departRButton);

        // Testing TreeTableView
        progCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        try {
            Program prog = new Program(DataBase.INSTANCE.getNewID(EDBTypeCode.PROGRAM));
            prog.setName("TEST PROG");
            TreeItem<Program> item = new TreeItem<>(prog);
            tableView.setRoot(item);
        }catch (Exception e){

        }
*/


        departmentCol.setCellValueFactory(new PropertyValueFactory("departmentName"));
        programCol.setCellValueFactory(new PropertyValueFactory("programName"));
        courseCol.setCellValueFactory(new PropertyValueFactory("courseName"));
        codeCol.setCellValueFactory(new PropertyValueFactory("courseCode"));
        requiredCol.setCellValueFactory(new PropertyValueFactory("required"));
        yearCol.setCellValueFactory(new PropertyValueFactory("courseYear"));

        List<CombinedData> data = new MainController().getData();

        for (CombinedData dat : data){
            tableList.add(dat);
        }

        System.out.println(tableList);

        table.setItems(tableList);


        new JMetro(JMetro.Style.LIGHT).applyTheme(root);

    }

    // Temporary information to use before loading from database
    public void updateSelectedItemInfo() {
        /*
        prereqListView.getItems().addAll(new ArrayList<>(Arrays.asList("AAAA111", "BBBB222", "CCCC333", "DDDD444", "EEEE555","FFFF666")));
        antireqListView.getItems().addAll(new ArrayList<>(Arrays.asList("XXXX123", "YYYY456", "ZZZZ789")));
        descriptionLabel.setText("Introduction into the development and evolution of software. Covers key conceptual foundations as well as key methods and techniques used in the different phases of the software lifecycle. Technologies are selected based on their fitness for purpose towards explicitly stated project objectives for different types of project. Emphasis is on both technical and soft skill needed for high quality software and software-based products developed in teams.");
        */
    }

    // Logs out of account and opens the login screen
    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("interface/loginView.fxml"));
        Parent parent = loader.load();
        Scene newScene = new Scene(parent);
        Stage userStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        userStage.setScene(newScene);
        userStage.show();
    }


    public void add(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/interface/addView.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

/*
    // Runs when the add button is clicked
    public void add() {
        if (this.actionGroup.getSelectedToggle().equals(this.departRButton)) {
            addDepartment();
        } else if (this.actionGroup.getSelectedToggle().equals(this.programRButton)) {
            addProgram();
        } else if (this.actionGroup.getSelectedToggle().equals(this.courseRButton)) {
            addCourse();
        }
    }

    // Runs when the delete button is clicked
    public void delete() {
        if (this.actionGroup.getSelectedToggle().equals(this.departRButton)) {
            deleteDepartment();
        } else if (this.actionGroup.getSelectedToggle().equals(this.programRButton)) {
            deleteProgram();
        } else if (this.actionGroup.getSelectedToggle().equals(this.courseRButton)) {
            deleteCourse();
        }
    }

    // Runs when the edit button is clicked
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
    */
}