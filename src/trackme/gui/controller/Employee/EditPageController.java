/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller.Employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;
import trackme.bll.BLLManager;
import trackme.gui.model.UserModel;

/**
 *
 * @author mac
 */
public class EditPageController implements Initializable{

    @FXML
    private AnchorPane userfrontPane;
    @FXML
    private JFXButton addeditbtn;
    @FXML
    private ImageView showup;
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
    private JFXButton editbtn;
    @FXML
    private JFXComboBox<Project> editprojectcombobox;
    @FXML
    private JFXComboBox<Task> taskbox;
    @FXML
    private DatePicker date;
    @FXML
    private JFXTimePicker startTimePicker;
    @FXML
    private JFXTimePicker endTimePicker;
    @FXML
    private Label completelbl;
    @FXML
    private Label errorlbl;
    
    private Task task;
    private User user;
    private Project project;
    private BLLManager bllManager;
    private UserModel userModel;
    private TextField endtime;
    
    
    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/UserOverview.fxml";
    
            
      @Override
    public void initialize(URL location, ResourceBundle resources) {
        userModel = UserModel.getInstance();
        user = userModel.getLoggedInUser();
        usrnamelbl.setText(user.getName());
        this.bllManager = new BLLManager();
        setProjects();

    }
    
    
    /**
     * Project ComboBox setup
     */
     public void setProjects(){
     ObservableList<Project> projectList = FXCollections.observableArrayList(bllManager.getAllProjects());
        editprojectcombobox.getItems().clear();
        editprojectcombobox.getItems().addAll(projectList);
        editprojectcombobox.getSelectionModel().select(editprojectcombobox.getValue());
    }
    
    /**
     * Method for setting Tasks in combo box  
     * @param event
     * @throws SQLServerException 
     */
    @FXML
    private void setProjectCombobox(ActionEvent event){
         project = editprojectcombobox.getSelectionModel().getSelectedItem();
         setTaskCombobox(project);
    }

    /**
     * Method for setting up task combo box
     * @param project
     * @throws SQLServerException 
     */
    
   private void setTaskCombobox(Project project){
        ObservableList<Task> taskList = FXCollections.observableArrayList(bllManager.getTasksForProject(project));
        taskbox.getItems().clear();
        taskbox.getItems().addAll(taskList);
        taskbox.getSelectionModel().select(taskbox.getValue());
    
   }
   
  /**
   * Updating Data /Date, task, project, time/
   * @param event
   * @throws SQLServerException 
   */
   

    @FXML
    private void clickEditButton(ActionEvent event){
        
        
        if(date.getValue()!=null && startTimePicker.getValue()!=null && endTimePicker.getValue()!=null){
        LocalDate value = date.getValue();

        LocalTime startTime = startTimePicker.getValue();
        LocalTime endTime = endTimePicker.getValue();
        List<LocalDateTime> timeList = bllManager.calculateTime(startTime, endTime, value);
        LocalDateTime newStartTime = timeList.get(0);
        LocalDateTime newEndTime = timeList.get(1);
        System.out.println(newStartTime);
        System.out.println(newEndTime);
        
        bllManager.editTimeLog(user, project, task, newStartTime, newEndTime);
        completelbl.setVisible(true);
        errorlbl.setVisible(false);
        }
        else{
           errorlbl.setVisible(true);
           completelbl.setVisible(false);
        }

    }
    
    /**
     * Method that gets the selected task 
     * @param event 
     */

    @FXML
    private void setSelectedTask(ActionEvent event) {
        
        task = taskbox.getSelectionModel().getSelectedItem();
        
    }
    
    /**
     * Menu bar functionality methods
     * @param event 
     */
    @FXML
    private void setCloseMenubar(MouseEvent event) {
        usrmenubar.setVisible(false);
    }

    @FXML
    private void setMenuPopUp(MouseEvent event) {   
        usrmenubar.setVisible(true); 
    }

    
    /**
     * Opening scenes methods
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
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/trackme/gui/view/UserMainPage.fxml"));
        Parent root = loader.load();
        UserMainPageController ctrl = loader.getController();

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
    private void setEdit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/trackme/gui/view/EditPage.fxml"));
        Parent root = loader.load();
        EditPageController ctrl = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage closePreviousScene;
        closePreviousScene = (Stage) editbtn.getScene().getWindow();
        closePreviousScene.close();
    }

}
