/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import com.jfoenix.controls.JFXComboBox;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trackme.be.Project;
import trackme.be.User;
import trackme.bll.BLLManager;
import trackme.gui.model.UserModel;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class AddTaskPopUpController implements Initializable {

    private User user;
    private UserModel userModel;
    private BLLManager bllManager;
    @FXML
    private Button close;
    @FXML
    private Button addtaskbtn;
    @FXML
    private TextField taskname;
    @FXML
    private JFXComboBox<Project> combox;

    private String name;
    @FXML
    private TextField description;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // this.bllManager = new BLLManager();
      //  this.userModel = new UserModel();
     //   user = userModel.getLoggedInUser();
    }    

    @FXML
    private void setClosePopUp(ActionEvent event) {
        
        Stage loginStage;
        loginStage = (Stage) close.getScene().getWindow();
        loginStage.close();
        
    }

//    @FXML
//    private void addNewTask(ActionEvent event, Project project) throws SQLServerException {
//        bllManager.insertTaskForProject(project, name, description);
//    }
//
//    @FXML
//    private void setTaskName(ActionEvent event) {
//        name = taskname.getText();
//    }
//
//    @FXML
//    private void setDescription(ActionEvent event) {
//        description = descriptionField.getText();
//    }
//
//    @FXML
//    private void setProject(User user) throws SQLServerException {
//        ObservableList<Project> projectList = FXCollections.observableArrayList(bllManager.getUserProjectTime(user));
//        combox.getItems().clear();
//        combox.getItems().addAll(projectList);
//        combox.getSelectionModel().select(combox.getValue());
//    }

    @FXML
    private void addNewTask(ActionEvent event) {
    }
    
}
