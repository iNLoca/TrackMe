/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class AddTaskPopUpController implements Initializable {

    @FXML
    private Button close;
    @FXML
    private Button addtaskbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void setClosePopUp(ActionEvent event) {
        
        Stage loginStage;
        loginStage = (Stage) close.getScene().getWindow();
        loginStage.close();
        
    }

    @FXML
    private void addNewTask(ActionEvent event) {
    }
    
}
