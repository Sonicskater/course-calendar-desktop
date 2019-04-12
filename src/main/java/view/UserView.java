package view;

import controller.CombinedData;
import controller.MainController;
import model.database.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserView implements Initializable {
    private static final int STUDENT = 0;
    private static final int FACULTY = 1;
    private final MainController MAIN_CONTROLLER = new MainController();

    private int userType; // Type of user, either STUDENT or FACULTY
    @FXML private HBox facultyPane; // Pane with faculty actions

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button logoutButton;

    // Sidebar information
    @FXML private Label courseDesc;
    @FXML private Label programDesc;
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
        departmentCol.setCellValueFactory(new PropertyValueFactory("departmentName"));
        programCol.setCellValueFactory(new PropertyValueFactory("programName"));
        courseCol.setCellValueFactory(new PropertyValueFactory("courseName"));
        codeCol.setCellValueFactory(new PropertyValueFactory("courseCode"));
        requiredCol.setCellValueFactory(new PropertyValueFactory("required"));
        yearCol.setCellValueFactory(new PropertyValueFactory("courseYear"));

        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
            displayCourse((newValue!=null)? newValue : oldValue);
        });
        updateTable();

        // Set styling for elements
        table.setStyle("-fx-background-color: #F3FFFF;");
        facultyPane.setStyle("-fx-background-color: #404040;");
        addButton.setStyle("-fx-background-color: #99FF99;");
        deleteButton.setStyle("-fx-background-color: #FF9999;");
        logoutButton.setStyle("-fx-background-color: #FF9999;");
    }

    private void displayCourse(CombinedData newValue) {
        courseDesc.setText(newValue.course.getDescription());
        ArrayObservableList<String> preList = new ArrayObservableList<>();
        for (DBUniqueID id : newValue.course.getPreReqs()) {
            try {
                preList.add(DataBase.INSTANCE.getCourse(id).getName());
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }
        }
        prereqListView.setItems(preList);

        ArrayObservableList<String> antiList = new ArrayObservableList<>();
        for (DBUniqueID id : newValue.course.getAntiReqs()) {
            try {
                antiList.add(DataBase.INSTANCE.getCourse(id).getName());
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }
        }
        antireqListView.setItems(antiList);

        programDesc.setText(newValue.program.getDescription());
    }

    void updateTable() {
        List<CombinedData> data = MAIN_CONTROLLER.getData();
        tableList = new ArrayObservableList<>();
        tableList.addAll(data);
        System.out.println(tableList);
        table.setItems(tableList);
    }

    public void updateSidebarInfo() {
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
        Scene newScene = new Scene(parent, Color.RED);
        Stage userStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        userStage.setScene(newScene);
        userStage.show();
    }


    public void add(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("interface/addView.fxml"));
        Parent root = fxmlLoader.load();
        ((AddView)fxmlLoader.getController()).view = this;
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Adding Tool");
        stage.show();
    }


    public void delete(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("interface/deleteView.fxml"));
        Parent root = fxmlLoader.load();
        ((DeleteView)fxmlLoader.getController()).view = this;
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Deleting Tool");
        stage.show();
    }

    // Runs when the delete button is clicked
    public void delete() {
        System.out.println("1");
        CombinedData combinedData = table.getSelectionModel().selectedItemProperty().get();
        DataBase.INSTANCE.removeRelation(combinedData.program, combinedData.course);
        updateTable();
    }
}