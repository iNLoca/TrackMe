/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class UserMainPageController implements Initializable {

    @FXML
    private AnchorPane userfrontPane;
    @FXML
    private Button startbtn;
    @FXML
    private Button stopbtn;
    @FXML
    private Pane usrmenubar;
    @FXML
    private ImageView showup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        
    }    

    @FXML
    private void setMenuPopUp(MouseEvent event) {
        
                userfrontPane.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                usrmenubar.setVisible(true);
            } else {
                usrmenubar.setVisible(false);
            }
        });

    }
    
}
