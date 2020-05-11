/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class TaskTilesController implements Initializable {

    @FXML
    private Label tasknamelbl;
    @FXML
    private Label deslbl;
    @FXML
    private Label moneylbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        
     //   tasknamelbl.setText(tasktableview.getSelectionModel().getSelectedItem().getName());
    }    
    
}
