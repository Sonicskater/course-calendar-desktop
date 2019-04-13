package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.database.DBUniqueID;
import model.database.DataBase;
import model.database.IDTypeMismatchExcception;
import model.types.Course;
import model.types.Department;
import model.types.Program;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteView implements Initializable {
    @FXML Button courseButton;
    @FXML Button depButton;
    @FXML Button progButton;


    @FXML ComboBox<Course> course = new ComboBox<>();
    @FXML ComboBox<Program> programOfCourse = new ComboBox<>();
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


        program.setButtonCell(programConverter.call(null));
        program.setCellFactory(programConverter);
        programOfCourse.setButtonCell(programConverter.call(null));
        programOfCourse.setCellFactory(programConverter);
        programOfCourse.selectionModelProperty().addListener(
                (observable, oldValue, newValue) -> {

                }
        );

        course.setButtonCell(courseConverter.call(null));
        course.setCellFactory(courseConverter);
        course.setButtonCell(courseConverter.call(null));
        course.setCellFactory(courseConverter);

        department.setButtonCell(departmentConverter.call(null));
        department.setCellFactory(departmentConverter);

        courseButton.setStyle("-fx-background-color: #FF9999;");
        depButton.setStyle("-fx-background-color: #FF9999;");
        progButton.setStyle("-fx-background-color: #FF9999;");

        update();
    }


    public void comboAction() {
        Program program = null;
        try {
            program = DataBase.INSTANCE.getProgram(programOfCourse.getSelectionModel().getSelectedItem().getId());
        } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
            return;
        } catch (NullPointerException n){
            course.setItems(new ArrayObservableList<>());
        }
        ArrayObservableList<Course> courses = new ArrayObservableList<>();
        if (program == null){
            return;
        }
        for ( DBUniqueID id : program.getRequired()){
            try {
                courses.add(DataBase.INSTANCE.getCourse(id));
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }

        }
        for ( DBUniqueID id : program.getOptional()){
            try {
                courses.add(DataBase.INSTANCE.getCourse(id));
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }

        }

        course.setItems(courses);
    }

    public void removeCourse(){
        DataBase.INSTANCE.removeRelation(programOfCourse.getValue(),course.getValue());
        update();
        view.updateTable();
    }

    public void deleteCourse(){
        DataBase.INSTANCE.deleteFromID(course.getValue().getId());
        update();
        view.updateTable();
    }
    public void deleteProgram(){
        DataBase.INSTANCE.deleteFromID(program.getValue().getId());
        update();
        view.updateTable();
    }
    public void deleteDepartment(){
        DataBase.INSTANCE.deleteFromID(department.getValue().getId());
        update();
        view.updateTable();
    }

    private void update(){

        ArrayObservableList<Course> courses = new ArrayObservableList<>();

        for ( DBUniqueID id : DataBase.INSTANCE.getCourseIDs()){
            try {
                courses.add(DataBase.INSTANCE.getCourse(id));
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }

        }
        ArrayObservableList<Program> programs = new ArrayObservableList<>();

        for ( DBUniqueID id : DataBase.INSTANCE.getProgramIDs()){
            try {
                programs.add(DataBase.INSTANCE.getProgram(id));
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }

        }
        ArrayObservableList<Department> departments = new ArrayObservableList<>();

        for ( DBUniqueID id : DataBase.INSTANCE.getDepartmentIDs()){
            try {
                departments.add(DataBase.INSTANCE.getDepartment(id));
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }

        }

        department.setItems(departments);
        programOfCourse.setItems(programs);
        program.setItems(programs);
        course.setItems(courses);

    }
}