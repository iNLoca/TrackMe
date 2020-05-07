/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.image.Image;
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
    private JFXComboBox<Project> projectbox;
    @FXML
    private JFXButton addtaskbtn;
    @FXML
    private TableView<Task> tasktableview;
    @FXML
    private TableColumn<Task, String> taskcolmn;
    @FXML
    private Button logoutbtn;
    @FXML
    private JFXButton overviewbtn;
    @FXML
    private JFXButton trackerbtn;

    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/UserOverview.fxml";
    private final String AddTask = "/trackme/gui/view/AddTaskPopUp.fxml";

    private ScheduledExecutorService absenceThreadExecutor;
    /**
     * Initializes the controller class.
     */
    private UserModel userModel;
    private TaskModel taskModel;
    private ProjectModel projectModel;
    @FXML
    private ImageView imgbtnplaypause;
    @FXML
    private Label usrnamelbl;

    private User user;
    private Project project;

    private BLLManager bllManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userModel = UserModel.getInstance();
        user = userModel.getLoggedInUser();
        usrnamelbl.setText(user.getName());
        this.bllManager = new BLLManager();

        // try {
        //projectModel.getUserProjectTime(us);
        //} catch (SQLServerException ex) {
        //Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        //}
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

    @FXML
    private void setMenuPopUp(MouseEvent event) {
        usrmenubar.setVisible(true);

    }

    @FXML
    private void setCloseMenubar(MouseEvent event) {

        usrmenubar.setVisible(false);

    }

    private void setProjectsInCombobox(User user) throws SQLServerException, SQLException {
        ObservableList<Project> projectList = FXCollections.observableArrayList(bllManager.getUserProjectTime(user));
        projectbox.getItems().clear();
        projectbox.getItems().addAll(projectList);
        projectbox.getSelectionModel().select(projectbox.getValue());
    }

    @FXML
    private void setSelectedProjects(ActionEvent event) {
        

    }

    private void setTaskTableView(Project project) throws SQLServerException {
        ObservableList<Task> taskList = FXCollections.observableArrayList(taskModel.getTasksForProject(project));

        taskcolmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tasktableview.setItems(taskList);

    }

    @FXML
    private void setSelectTask(MouseEvent event) {
        //TODO - Fetch data for Pane / Visible (true) else false;

    }

    @FXML
    private void setAddNewTask(ActionEvent event) throws IOException {

        FXMLLoader fxmll = new FXMLLoader(getClass().getResource(AddTask));
        Parent root = fxmll.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

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
    private boolean LOl = false;

    @FXML
    private void PressStartPause(ActionEvent event) throws InterruptedException {
        String imageSource;
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

            imageSource = "/trackme/gui/icons/pause.png";
        } else {

            imageSource = "/trackme/gui/icons/play.png";
            //LOl = false;
            // ThreadSleep();
        }
        startbtn.setGraphic(new ImageView(new Image(imageSource)));

        //       absenceThreadExecutor.schedule((Runnable) startbtn, 4, TimeUnit.SECONDS);
    }

    @FXML
    private void PressStop(ActionEvent event) {
        LOl = false;
        absenceThreadExecutor.shutdown();
    }

    /*
    private void PressPause(){
        if(LOl=true && startbtn!=null){
        LOl=false;       
        absenceThreadExecutor.schedule(() -> {
        }, 0, TimeUnit.HOURS);
    
        }
    }
     */
    private void ThreadSleep() throws InterruptedException {

        long startTime = System.currentTimeMillis();
       // Thread.sleep(5000);
        System.out.println("Sleep time in ms = " + (System.currentTimeMillis() - startTime));

    }

}
