/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;
import trackme.bll.BLLManager;

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
    @FXML
    private JFXButton startbtn;

    
    
     private ScheduledExecutorService absenceThreadExecutor;
     private BLLManager bllManager;
     private User user;
    private Project project;
    private Task task;
    
    private Label timecountlbl; ///Wrong 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        
       
    }    
private boolean LOl = false;


    @FXML
    private void setStart(ActionEvent event) {
        
         if (startbtn != null && !LOl) {
            LOl = true;
            long startTime = System.currentTimeMillis();
            absenceThreadExecutor = Executors.newSingleThreadScheduledExecutor();
            absenceThreadExecutor.scheduleAtFixedRate(() -> {
                Platform.runLater(() -> {

                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long elapsedSeconds = (elapsedTime / 1000) % 60;
                    long elapsedMinutes = ((elapsedTime / 1000) / 60) % 60;
                    long elapsedHours = (((elapsedTime / 1000) / 60) / 60) % 24;

                 timecountlbl.setText(elapsedHours + " : " + elapsedMinutes + " : " + elapsedSeconds);
                });
            }, 1, 1, TimeUnit.SECONDS);

                     try {
                         bllManager.insertTimeLog(user,project ,task , 0);
                     } catch (SQLServerException ex) {
                         Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                     }
         }
    }
}

   
             
    

    

