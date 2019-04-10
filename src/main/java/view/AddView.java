package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.database.DBUniqueID;
import model.database.DataBase;
import model.database.EDBTypeCode;
import model.database.IDTypeMismatchExcception;
import model.types.Course;
import model.types.Department;
import model.types.Program;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddView implements Initializable {

    @FXML
    TextField depName;



    @FXML
    ComboBox<Department> departmentComboBox = new ComboBox<>();

    @FXML
    ComboBox<Course> courseToAdd;

    @FXML
    ComboBox<Program> programToAddTo;

    @FXML
    TextField progName;

    @FXML
    TextArea progDesc;


    public UserView view;

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

    public void updateUi(){

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
        ArrayObservableList<Program> progs = new ArrayObservableList<>();
        for (DBUniqueID id :
                ids) {
            try {
                Program d = DataBase.INSTANCE.getProgram(id);
                progs.add(d);
            } catch (IDTypeMismatchExcception idTypeMismatchExcception) {
                idTypeMismatchExcception.printStackTrace();
            }
        }

        programToAddTo.setItems(progs);

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

        courseToAdd.setItems(courses);
    }


}
