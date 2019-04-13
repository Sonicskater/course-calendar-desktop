package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.database.DBUniqueID;
import model.database.DataBase;
import model.database.EDBTypeCode;
import model.database.IDTypeMismatchExcception;
import model.types.Course;
import model.types.Department;
import model.types.Program;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddView implements Initializable {
    @FXML ComboBox<Department> departmentComboBox = new ComboBox<>();
    @FXML ComboBox<Course> reqCourse = new ComboBox<>();
    @FXML ComboBox<Course> modCourse = new ComboBox<>();
    @FXML ComboBox<Course> courseToAdd;
    @FXML ComboBox<Program> programToAddTo;

    @FXML TextField depName;
    @FXML TextField progName;
    @FXML TextArea progDesc;
    @FXML TextField courseTitle;
    @FXML TextField courseCode;
    @FXML TextArea courseDesc;
    @FXML TextField courseYear;

    @FXML Button depButton;
    @FXML Button progButton;
    @FXML Button reqButton;
    @FXML Button optButton;
    @FXML Button courseButton;
    @FXML Button antiReqButton;
    @FXML Button preReqButton;

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

        departmentComboBox.setButtonCell(departmentConverter.call(null));
        departmentComboBox.setCellFactory(departmentConverter);

        programToAddTo.setButtonCell(programConverter.call(null));
        programToAddTo.setCellFactory(programConverter);

        courseToAdd.setButtonCell(courseConverter.call(null));
        courseToAdd.setCellFactory(courseConverter);

        reqCourse.setButtonCell(courseConverter.call(null));
        reqCourse.setCellFactory(courseConverter);

        modCourse.setButtonCell(courseConverter.call(null));
        modCourse.setCellFactory(courseConverter);

        depButton.setStyle("-fx-background-color: #ADD8E6;");
        progButton.setStyle("-fx-background-color: #ADD8E6;");
        reqButton.setStyle("-fx-background-color: #FF9999;");
        optButton.setStyle("-fx-background-color: #99FF99;");
        courseButton.setStyle("-fx-background-color: #ADD8E6;");
        antiReqButton.setStyle("-fx-background-color: #FF9999;");
        preReqButton.setStyle("-fx-background-color: #99FF99;");

        updateUi();
    }


    public void addDep(){
        try {
            Department dep = new Department(new DBUniqueID(EDBTypeCode.DEPARTMENT));
            dep.setName(depName.getText());
            dep.save();
        } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
            idTypeMismatchExcception.printStackTrace();
        }
        view.updateTable();
        updateUi();
    }

    public void addProg(){
        try{
            Program program = new Program(new DBUniqueID(EDBTypeCode.PROGRAM));

            Department selectedItem = departmentComboBox.getSelectionModel().getSelectedItem();
            selectedItem.addProgram(program.getId());
            selectedItem.save();

            program.setName(progName.getText());
            program.setDescription(progDesc.getText());
            program.save();

        } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
            idTypeMismatchExcception.printStackTrace();
        }
        view.updateTable();
        updateUi();
    }

    public void addReq(){

        Program selectedProgram = programToAddTo.getSelectionModel().getSelectedItem();

        Course selectedCourse = courseToAdd.getSelectionModel().getSelectedItem();

        try {
            selectedProgram.addRequired(selectedCourse.getId());
        }catch (Exception e){
            //No course selected.
        }


        selectedProgram.save();
        view.updateTable();
        updateUi();
    }

    public void addOpt(){
        Program selectedProgram = programToAddTo.getSelectionModel().getSelectedItem();

        Course selectedCourse = courseToAdd.getSelectionModel().getSelectedItem();

        try {
            selectedProgram.addOptional(selectedCourse.getId());
        }catch (Exception e){
            //No course selected.
        }
        selectedProgram.save();
        view.updateTable();
        updateUi();
    }

    public void addCourse(){
        try {
            Course course = new Course(new DBUniqueID(EDBTypeCode.COURSE));

            course.setCode(courseCode.getText());
            course.setTitle(courseTitle.getText());
            course.setDescription(courseDesc.getText());
            course.setYear(Integer.parseInt(courseYear.getText()));

            course.save();
        } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
            idTypeMismatchExcception.printStackTrace();
        }
        view.updateTable();
        updateUi();
    }

    public void addAnti(){
        Course selectedItem = modCourse.getSelectionModel().getSelectedItem();

        selectedItem.addAntiReq(reqCourse.getSelectionModel().getSelectedItem().getId());

        selectedItem.save();
        view.updateTable();
        updateUi();
    }

    public void addPre(){
        Course selectedItem = modCourse.getSelectionModel().getSelectedItem();

        selectedItem.addPreReq(reqCourse.getSelectionModel().getSelectedItem().getId());

        selectedItem.save();
        view.updateTable();
        updateUi();
    }

    private void updateUi(){

        List<DBUniqueID> ids = DataBase.INSTANCE.getDepartmentIDs();
        ArrayObservableList<Department> deps = new ArrayObservableList<>();
        for (DBUniqueID id :
                ids) {
            try {
                Department d = DataBase.INSTANCE.getDepartment(id);
                deps.add(d);
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }
        }

        departmentComboBox.setItems(deps);

        ids = DataBase.INSTANCE.getProgramIDs();
        ArrayObservableList<Program> programs = new ArrayObservableList<>();
        for (DBUniqueID id :
                ids) {
            try {
                Program d = DataBase.INSTANCE.getProgram(id);
                programs.add(d);
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }
        }

        programToAddTo.setItems(programs);

        ids = DataBase.INSTANCE.getCourseIDs();
        ArrayObservableList<Course> courses = new ArrayObservableList<>();
        for (DBUniqueID id :
                ids) {
            try {
                Course d = DataBase.INSTANCE.getCourse(id);
                courses.add(d);
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }
        }

        reqCourse.setItems(courses);
        courseToAdd.setItems(courses);
        modCourse.setItems(courses);
    }
}
