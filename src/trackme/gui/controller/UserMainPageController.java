/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    @FXML
    private Label timecountlbl;
    @FXML
    private JFXComboBox<?> projectbox;
    @FXML
    private JFXButton addtaskbtn;
    @FXML
    private TableColumn<?, ?> tasktableview;
    @FXML
    private Button logoutbtn;
    @FXML
    private JFXButton overviewbtn;
    @FXML
    private JFXButton trackerbtn;

    
    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/UserOverview.fxml";
    private final String AddTask = "/trackme/gui/view/AddTaskPopUp.fxml";
   
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void setMenuPopUp(MouseEvent event) {
        
        userfrontPane.hoverProperty().addListener((ChangeListener<Boolean>)(observable,OldValue,newValue)->{
          if(newValue){
            usrmenubar.setVisible(true);
          } else{
            usrmenubar.setVisible(false);
          }
        });
        
        
    }

    @FXML
    private void setCloseMenubar(MouseEvent event) {
        
       userfrontPane.hoverProperty().addListener((ChangeListener<Boolean>)(observable, OldValue,newValue)->{
           if(newValue){
             usrmenubar.setVisible(false);
           }else{
            usrmenubar.setVisible(false);
           }
       
       });
        
    }

    @FXML
    private void setProjectComboBox(ActionEvent event) {
    }

    @FXML
    private void setAddNewTask(ActionEvent event) throws IOException {
        
        
        
        FXMLLoader fxmll = new FXMLLoader(getClass().getResource(AddTask));
        Parent root = fxmll.load();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void setOverview(ActionEvent event) throws IOException {
        Stage closePreviousScene;
        closePreviousScene = (Stage)overviewbtn.getScene().getWindow();
        closePreviousScene.close();
        
        FXMLLoader fxloader = new FXMLLoader(getClass().getResource(OverviewScene));
        Parent root =fxloader.load();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        
    }

    @FXML
    private void setFrontPage(ActionEvent event) throws IOException {
       
        Stage closePreviousScene;
        closePreviousScene = (Stage)trackerbtn.getScene().getWindow();
        closePreviousScene.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/trackme/gui/view/UserMainPage.fxml"));
        Parent root = loader.load();
        UserMainPageController ctrl = loader.getController();
        

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void setLogOutusr(ActionEvent event) throws IOException {
       
        Stage logOutUser;
        logOutUser = (Stage)logoutbtn.getScene().getWindow();
        logOutUser.close();
        
        URL url = getClass().getResource(LoginScene);
        FXMLLoader fxmlload = new FXMLLoader();
        fxmlload.setLocation(url);
        Parent root = fxmlload.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        
        
    }
    
}
