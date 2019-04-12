package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.database.DBUniqueID;
import model.database.EDBTypeCode;
import model.database.IDTypeMismatchExcception;
import model.types.Course;
import model.types.Department;
import model.types.Program;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteView implements Initializable {
    @FXML Button courseButton;
    @FXML Button prereqButton;
    @FXML Button antireqButton;
    @FXML Button depButton;
    @FXML Button progButton;


    @FXML ComboBox<Course> course = new ComboBox<>();
    @FXML ComboBox<Program> programOfCourse = new ComboBox<>();
    @FXML ComboBox<Course> prereq = new ComboBox<>();
    @FXML ComboBox<Course> prereqOfCourse = new ComboBox<>();
    @FXML ComboBox<Course> antireq = new ComboBox<>();
    @FXML ComboBox<Course> antireqOfCourse = new ComboBox<>();
    @FXML ComboBox<Department> department = new ComboBox<>();
    @FXML ComboBox<Program> program = new ComboBox<>();

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

        course.setButtonCell(courseConverter.call(null));
        course.setCellFactory(courseConverter);

        course.setButtonCell(courseConverter.call(null));
        course.setCellFactory(courseConverter);

        prereq.setButtonCell(courseConverter.call(null));
        prereq.setCellFactory(courseConverter);

        prereqOfCourse.setButtonCell(courseConverter.call(null));
        prereqOfCourse.setCellFactory(courseConverter);

        antireq.setButtonCell(courseConverter.call(null));
        antireq.setCellFactory(courseConverter);

        antireqOfCourse.setButtonCell(courseConverter.call(null));
        antireqOfCourse.setCellFactory(courseConverter);

        department.setButtonCell(departmentConverter.call(null));
        department.setCellFactory(departmentConverter);

        program.setButtonCell(programConverter.call(null));
        program.setCellFactory(programConverter);

        courseButton.setStyle("-fx-background-color: #FF9999;");
        prereqButton.setStyle("-fx-background-color: #FF9999;");
        antireqButton.setStyle("-fx-background-color: #FF9999;");
        progButton.setStyle("-fx-background-color: #FF9999;");
        depButton.setStyle("-fx-background-color: #FF9999;");

        updateUi();
    }

    public void deleteCourse(){
        System.out.println("deleting course");
        view.updateTable();
        updateUi();
        // TODO remove course by program
        view.updateTable();
        updateUi();
    }

    public void deletePrereq(){
        /*try{
            DBUniqueID prereq = prereq.getSelectionModel().getSelectedItem().getCode();
            Course course = prereqOfCourse.getSelectionModel().getSelectedItem();
            course.removePreReq(prereq);
        } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
            idTypeMismatchExcception.printStackTrace();
        }*/
        view.updateTable();
        updateUi();



        System.out.println("deleting prereq");
        view.updateTable();
        updateUi();
        // TODO remove prereq from course
        view.updateTable();
        updateUi();
    }

    public void deleteAntireq(){
        System.out.println("deleting antireq");
        view.updateTable();
        updateUi();
        // TODO remove antireq from course
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
