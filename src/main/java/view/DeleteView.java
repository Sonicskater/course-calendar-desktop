package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.types.Course;
import model.types.Department;
import model.types.Program;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteView implements Initializable {
    @FXML Button courseButton;
    @FXML Button depButton;
    @FXML Button progButton;

    @FXML ComboBox<Course> courseComboBox = new ComboBox<>();
    @FXML ComboBox<Program> programOfCourseComboBox = new ComboBox<>();
    @FXML ComboBox<Department> departmentComboBox = new ComboBox<>();
    @FXML ComboBox<Program> programComboBox = new ComboBox<>();

    UserView view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Callback<ListView<Program>, ListCell<Program>> programConverter = param -> new ListCell<Program>() {
            @Override
            protected void updateItem(Program item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        };
        Callback<ListView<Course>, ListCell<Course>> courseConverter = param -> new ListCell<Course>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        };

        Callback<ListView<Department>, ListCell<Department>> departmentConverter = param -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        };

        courseComboBox.setButtonCell(courseConverter.call(null));
        courseComboBox.setCellFactory(courseConverter);

        programOfCourseComboBox.setButtonCell(programConverter.call(null));
        programOfCourseComboBox.setCellFactory(programConverter);

        departmentComboBox.setButtonCell(departmentConverter.call(null));
        departmentComboBox.setCellFactory(departmentConverter);

        programComboBox.setButtonCell(programConverter.call(null));
        programComboBox.setCellFactory(programConverter);

        courseButton.setStyle("-fx-background-color: #FF9999;");
        progButton.setStyle("-fx-background-color: #FF9999;");
        depButton.setStyle("-fx-background-color: #FF9999;");

        updateUi();
    }

    public void deleteCourse(){
        System.out.println("deleting course");
        // TODO remove course by program
        view.updateTable();
        updateUi();
    }

    public void deleteDep(){
        System.out.println("deleting department");
        // TODO remove department
        view.updateTable();
        updateUi();
    }

    public void deleteProg(){
        System.out.println("deleting program");
        // TODO remove program
        view.updateTable();
        updateUi();
    }

    private void updateUi(){
//        List<DBUniqueID> ids = DataBase.INSTANCE.getDepartmentIDs();
//        ArrayObservableList<Department> deps = new ArrayObservableList<>();
//        for (DBUniqueID id :
//                ids) {
//            try {
//                Department d = DataBase.INSTANCE.getDepartment(id);
//                deps.add(d);
//            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
//                idTypeMismatchExcception.printStackTrace();
//            }
//        }
//
//        departmentComboBox.setItems(deps);
//
//        ids = DataBase.INSTANCE.getProgramIDs();
//        ArrayObservableList<Program> programs = new ArrayObservableList<>();
//        for (DBUniqueID id :
//                ids) {
//            try {
//                Program d = DataBase.INSTANCE.getProgram(id);
//                programs.add(d);
//            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
//                idTypeMismatchExcception.printStackTrace();
//            }
//        }
//
//        programToAddTo.setItems(programs);
//
//        ids = DataBase.INSTANCE.getCourseIDs();
//        ArrayObservableList<Course> courses = new ArrayObservableList<>();
//        for (DBUniqueID id :
//                ids) {
//            try {
//                Course d = DataBase.INSTANCE.getCourse(id);
//                courses.add(d);
//            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
//                idTypeMismatchExcception.printStackTrace();
//            }
//        }
//
//        reqCourse.setItems(courses);
//        courseToAdd.setItems(courses);
//        modCourse.setItems(courses);
    }
}
