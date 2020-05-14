/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.chart.BarChart;
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
import trackme.be.User;
import trackme.be.User.UserType;
import trackme.bll.BLLManager;
import trackme.gui.controller.Employee.UserMainPageController;
import trackme.gui.model.UserModel;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class AdminProfilesController implements Initializable {

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
    
     private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/AdminOverview.fxml";
    private User user;
    private UserModel userModel;
    private BLLManager bllManager;
    
    private String userName;
    private String userPassword;
    private String email;
    private String checkidadmin;
    private String checkemplid;
    @FXML
    private TableView<User> usrtableview;
    @FXML
    private TableColumn<User, String> usrname;
    @FXML
    private TableColumn<User, UserType> usrtype;
    @FXML
    private JFXTextField namefield;
    @FXML
    private JFXPasswordField passfield;
    @FXML
    private JFXTextField emailfield;
    @FXML
    private JFXCheckBox admincheck;
    @FXML
    private JFXButton addUser;
    @FXML
    private JFXButton deletebtn;
    @FXML
    private Label errorlbl;
    @FXML
    private JFXButton editusr;
    @FXML
    private JFXCheckBox employeecheck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       userModel = UserModel.getInstance();
        user = userModel.getLoggedInUser();
        usrnamelbl.setText(user.getName());
        this.bllManager = new BLLManager();
        try {
            setUserTableView();
        } catch (SQLServerException ex) {
            Logger.getLogger(AdminProfilesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    public void setUserTableView() throws SQLServerException{
    
        ObservableList<User> userList = FXCollections.observableArrayList(bllManager.getAllUsers());
        usrname.setCellValueFactory(new PropertyValueFactory<>("name"));
        usrtype.setCellValueFactory(new PropertyValueFactory<>("type"));

        usrtableview.setItems(userList);

    
    }
    
     @FXML
    private void selectUser(MouseEvent event) throws SQLServerException {
     
         namefield.setText(usrtableview.getSelectionModel().getSelectedItem().getName());
         emailfield.setText(usrtableview.getSelectionModel().getSelectedItem().getEmail());
         passfield.setText(usrtableview.getSelectionModel().getSelectedItem().getPassword());
          //Change getAllUsers() in UserDAO ? 
         
         admincheck.setUserData(usrtableview.getSelectionModel().getSelectedItem().getType().compareTo(UserType.ADMIN));
         admincheck.setSelected(true);
         System.out.println("check admin");
        
        
       
         /*
         admincheck.setUserData(usrtableview.getSelectionModel().getSelectedItem().getType());
         employeecheck.setUserData(usrtableview.getSelectionModel().getSelectedItem().getType());
        */
    }

 
   
    @FXML
    private void setAddUser(ActionEvent event) { 
        
        
         if(user!=null && (emailfield!=null && passfield!=null && namefield!=null && (admincheck.isSelected() || employeecheck.isSelected()))){
        
                saveData();
      
        }else {
        
             errorlbl.setVisible(true);
         }
         
      
    }
    
    
    
    public void saveData() {
     
                   // bllManager.insertTaskForProject(project, insertTasklbl.getText(), Descriplbl.getText(), 0);
                 
                    userName = namefield.getText();
                    userPassword = passfield.getText();
                    email = emailfield.getText();
                    if(admincheck.isSelected()){
                     checkidadmin=admincheck.getId();
                       System.out.println("Admin id");
                    }else if(employeecheck.isSelected()){
                    checkemplid= employeecheck.getId();
                         System.out.println("Employee Id");
                    }
                    
                    
                    System.out.println("data is saved");
                    
                    namefield.clear();
                    passfield.clear();
                    emailfield.clear();
                    admincheck.setSelected(false);
                    employeecheck.setSelected(false);
                    errorlbl.setVisible(false);
         
    }

  @FXML
    private void setAdmin(ActionEvent event) {
       if(admincheck.isSelected()){
         employeecheck.setSelected(false);
         
         
      
       }
    }

    @FXML
    private void setEmployee(ActionEvent event) {
        if(employeecheck.isSelected()){
          admincheck.setSelected(false);
        }
        
    }

    @FXML
    private void setDeleteUser(ActionEvent event) {
        
        int a= usrtableview.getSelectionModel().getSelectedItem().getId(); 
       // bllManager.deleteUserById(a); make UserDAO.deleteUser()
 
    }

    @FXML
    private void setEditUser(ActionEvent event) {
        
      if(user!=null && (emailfield!=null && passfield!=null && namefield!=null && (admincheck.isSelected() || employeecheck.isSelected()))){
        
                saveData();
          
        }else {
        
             errorlbl.setVisible(true);
         }
         
      
    }  //saves new written data over old one 
      
        // bllManager.addEditUser(user.getId(), namefield.getText(),emailfield.getText(), passfield.getText(),0);
        
    
    

    

    @FXML
    private void setShowMenubar(MouseEvent event) {
        usrmenubar.setVisible(true);
    }
    

    
    @FXML
    private void setCloseMenubar(MouseEvent event) {
        usrmenubar.setVisible(false);
    }
    
    
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

}
