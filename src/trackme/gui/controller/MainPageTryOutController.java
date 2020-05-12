/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledExecutorService;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;
import trackme.bll.BLLManager;
import trackme.gui.model.ProjectModel;
import trackme.gui.model.TaskModel;
import trackme.gui.model.UserModel;

/**
 *
 * @author mac
 */
public class MainPageTryOutController implements Initializable {

    @FXML
    private AnchorPane userfrontPane;
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
    private Pane MainPane;
    @FXML
    private Button stopbtn;
    @FXML
    private Label timecountlbl;
    @FXML
    private JFXComboBox<Project> projectbox;
    @FXML
    private JFXTextField insertTasklbl;
    @FXML
    private JFXTextField Descriplbl;
    @FXML
    private CheckBox checkmoney;
    @FXML
    private Pane TilesPane;
    
    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/UserOverview.fxml";
    private ScheduledExecutorService absenceThreadExecutor;
    private UserModel userModel;
    private TaskModel taskModel;
    private ProjectModel projectModel;
    private User user;
    private Project project;
    private Task task;
    private BLLManager bllManager;
    private Label tasknamelbl;
    private Label descriptionlbl;
    private Label introtasklbl;
    private Label introdeslbl;
    
   
    
    @Override
 public void initialize(URL url, ResourceBundle rb) {
        userModel = UserModel.getInstance();
        user = userModel.getLoggedInUser();
        usrnamelbl.setText(user.getName());
        this.bllManager = new BLLManager();
        
         try {
            setProjectsInCombobox(user);
            //setTaskTableView(project);
        } catch (SQLServerException ex) {
            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("not working");
        } catch (SQLException ex) {
            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

 }

  private void setProjectsInCombobox(User user) throws SQLServerException, SQLException {
        ObservableList<Project> projectList = FXCollections.observableArrayList(bllManager.getUserProjectTime(user));
        projectbox.getItems().clear();
        projectbox.getItems().addAll(projectList);
        projectbox.getSelectionModel().select(projectbox.getValue());
    }
  
 @FXML
    private void setSelectedProjects(ActionEvent event) throws SQLServerException, IOException {
        project = projectbox.getSelectionModel().getSelectedItem();
       setTasksPane(project);

    }
    private void setSelectTask(MouseEvent event) {
        
        introtasklbl.setVisible(true);
        introdeslbl.setVisible(true);

    // tasknamelbl.setText(tasktableview.getSelectionModel().getSelectedItem().getName());
    // descriptionlbl.setText(tasktableview.getSelectionModel().getSelectedItem().getDescription());

    }

    private void setTasksPane(Project project) throws IOException{
        List<Task> taskList = project.getTaskForProject();
        for (Task task1 : taskList) {
            Pane addTasksPane =   FXMLLoader.load(getClass().getResource("/trackme/gui/view/TaskTiles.fxml"));
            TilesPane.getChildren().add(addTasksPane);  
        }
       //fore loop
       
        
    
    }
    
    @FXML
    private void setMenuPopUp(MouseEvent event) {
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
private boolean LOl = false;
    @FXML
    private void PressStop(ActionEvent event) throws SQLServerException {
        LOl = false;
        absenceThreadExecutor.shutdown();
        bllManager.insertTimeLog(user,project ,task , 1);
    }

    
}
