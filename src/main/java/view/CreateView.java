package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.database.DBUniqueID;
import model.database.EDBTypeCode;
import model.database.IDTypeMismatchExcception;
import model.types.Department;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateView implements Initializable {

    @FXML
    TextField depName;



    @FXML
    ComboBox<Department> departmentComboBox;


    UserView view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departmentComboBox.setCellFactory(new PropertyValueFactory("name"));
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
    }

    public void addProg(){

    }

    public void updateUi(){

    }


}
