/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
public class AdminCreateController implements Initializable {

    @FXML
    private AnchorPane OverviewUser;
    @FXML
    private ImageView menubar;
    @FXML
    private Pane usrmenubar;
    @FXML
    private Label usrnamelbl;
    @FXML
    private JFXButton overviewbtn;
    @FXML
    private JFXButton trackerbtn;
    @FXML
    private Button logoutbtn;
    @FXML
    private JFXButton createbtn;
    @FXML
    private JFXButton profilesbtn;
    
    @FXML
    private JFXTextField clientname;
    @FXML
    private JFXTextField peojectname;
    @FXML
    private JFXTextField hfeelbl;
    @FXML
    private JFXButton addbtn;
    @FXML
    private TableView<Project> createtableview;
    @FXML
    private TableColumn<Project, String> clientcolumn;
    @FXML
    private TableColumn<Project, String> projectcolmn;
    @FXML
    private TableColumn<Project, Integer> feecolmn;
    @FXML
    private Label errormsg;  
   
    private User user;
    private UserModel userModel;
    private BLLManager bllManager;
    private String clientnm;
    private String projectps;
    private String fee;
    
    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/AdminOverview.fxml";
   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userModel = UserModel.getInstance();
        user = userModel.getLoggedInUser();
        usrnamelbl.setText(user.getName());
        this.bllManager = new BLLManager();
        setTableView();
        
    }    

    
    /**
     * Setting Table functionality
     * @throws SQLException 
     */
    
      public void setTableView(){
    
    
        ObservableList<Project> projectList = FXCollections.observableArrayList(bllManager.getAllProjects());
        projectcolmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientcolumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        feecolmn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        createtableview.setItems(projectList);
    
    }
      
     
      /**
       * Method that saves the data in DB
       * @throws SQLException 
       */
      
     public void saveNewData(){
    
                    clientnm = clientname.getText();
                    projectps = peojectname.getText();
                    fee = hfeelbl.getText();
                    
                   
                    bllManager.createProject(projectps, clientnm, fee);
                    
                  
                    setTableView();
                    
                    clientname.clear();
                    peojectname.clear();
                    hfeelbl.clear();
                    errormsg.setVisible(false);
 
}
     
 /**
  * Creating a new Client
  * @param event
  * @throws SQLException 
  */        
         
    @FXML
    private void setAddCreate(ActionEvent event){
        
         if(user!=null && (!clientname.getText().isEmpty() && !peojectname.getText().isEmpty() && !hfeelbl.getText().isEmpty())){
          
                saveNewData();
      
            }else{
        
            errormsg.setVisible(true);
         }
         
        
    }
    
 
    
    /**
     * Menu bar methods
     * @param event 
     */
    
    @FXML
    private void setShowMenubar(MouseEvent event) {
        usrmenubar.setVisible(true);
    }
    
     @FXML
    private void setCloseMenubar(MouseEvent event) {
        usrmenubar.setVisible(false);
    }
    
    /**
     * Opening Scene methods
     * @param event
     * @throws IOException 
     */

    @FXML
    private void setOverview(ActionEvent event) throws IOException {
        FXMLLoader fxloader = new FXMLLoader(getClass().getResource(OverviewScene));
        Parent root = fxloader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage closePreviousScene;
        closePreviousScene = (Stage) overviewbtn.getScene().getWindow();
        closePreviousScene.close();
    }

    @FXML
    private void setFrontPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/trackme/gui/view/AdminMainPage.fxml"));
        Parent root = loader.load();
        AdminMainPageController ctrl = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage closePreviousScene;
        closePreviousScene = (Stage) trackerbtn.getScene().getWindow();
        closePreviousScene.close();
    }

   

    @FXML
    private void setLogOutusr(ActionEvent event) throws IOException {
        Stage logOutUser;
        logOutUser = (Stage) logoutbtn.getScene().getWindow();
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

    @FXML
    private void setCreateScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/trackme/gui/view/AdminCreate.fxml"));
        Parent root = loader.load();
        AdminCreateController ctrl = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage closePreviousScene;
        closePreviousScene = (Stage) createbtn.getScene().getWindow();
        closePreviousScene.close();
    }

    @FXML
    private void setProfilesPage(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/trackme/gui/view/AdminProfiles.fxml"));
        Parent root = loader.load();
        AdminProfilesController ctrl = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage closePreviousScene;
        closePreviousScene = (Stage) profilesbtn.getScene().getWindow();
        closePreviousScene.close();
    }

    
    
}
