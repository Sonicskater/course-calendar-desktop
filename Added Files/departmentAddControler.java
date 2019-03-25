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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class departmentAddControler implements Initializable {

    @FXML private TextField DgetName;
    @FXML private TextField DgetID;
    @FXML private Button Dcreate;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }
    public void CreateD() {
    	String Name = DgetName.getText();
    	String dID = DgetID.getText();
    	try {
    		int IDnum = Integer.parseInt(dID);
    	}
    	catch (Exception e) {
    		//some error handeling
    	}
    	Department d = null;
        DBUniqueID id = new DBUniqueID(EDBTypeCode.DEPARTMENT);
        id.setNumCode(IDnum);
        try {
            d = new Department(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        d.setname(Name);
    }

    
}
