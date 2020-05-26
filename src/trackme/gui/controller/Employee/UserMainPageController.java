/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller.Employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private Pane usrmenubar;
    @FXML
    private ImageView showup;
    @FXML
    private Label timecountlbl;
    @FXML
    private JFXComboBox<Project> projectbox;

    @FXML
    private Button logoutbtn;
    @FXML
    private JFXButton overviewbtn;
    @FXML
    private JFXButton trackerbtn;
    @FXML
    private Label usrnamelbl;

    @FXML
    private JFXTextField insertTasklbl;
    @FXML
    private JFXTextField Descriplbl;
    @FXML
    private TableView<Task> tasktableview;
    @FXML
    private TableColumn<Task, String> taskcolmn;
    @FXML
    private TableColumn<Task, String> desccolm;
    @FXML
    private TableColumn<Task, Integer> moneycolmn;
    @FXML
    private TableColumn<Task, Void> colBtn;
    @FXML
    private TableColumn<?, ?> totaltimecolmn;

    @FXML
    private JFXButton editbtn;
    @FXML
    private CheckBox checkmoney;
    @FXML
    private JFXButton addtask;

    private UserModel userModel;
    private TaskModel taskModel;
    private ProjectModel projectModel;
    private User user;
    private Project project;
    private Task task;
    private Task name;
    private Task description;
    private Task toPay;
    private BLLManager bllManager;
    private Label tasknamelbl;
    private Label descriptionlbl;
    private Label introtasklbl;
    private Label introdeslbl;
    private String initialName;
    private String initialDescription;

    private ScheduledExecutorService ThreadExecutor;

    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/UserOverview.fxml";

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userModel = UserModel.getInstance();
        user = userModel.getLoggedInUser();
        usrnamelbl.setText(user.getName());
        this.bllManager = new BLLManager();

        try {
            setProjectsInCombobox(user);
            // setTaskTableView(project);
        } catch (SQLServerException ex) {
            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("not working");
        } catch (SQLException ex) {
            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Project Combo box setup method 
     * @param user
     * @throws SQLServerException
     * @throws SQLException 
     */
    private void setProjectsInCombobox(User user) throws SQLServerException, SQLException {
        ObservableList<Project> projectList = FXCollections.observableArrayList(bllManager.getProjectsForUser(user));
        projectbox.getItems().clear();
        projectbox.getItems().addAll(projectList);
        projectbox.getSelectionModel().select(projectbox.getValue());

    }
    
    @FXML
    private void setSelectedProjects(ActionEvent event) throws SQLServerException {
        project = projectbox.getSelectionModel().getSelectedItem();
        setTaskTableView(project);

    }

    /**
     * Setup Table Method
     * @param project
     * @throws SQLServerException 
     */
    private void setTaskTableView(Project project) throws SQLServerException {

        ObservableList<Task> taskList = FXCollections.observableArrayList(bllManager.getTasksForProject(project));
        taskcolmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        desccolm.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        moneycolmn.setCellValueFactory(new PropertyValueFactory<>("toPayImage"));

        tasktableview.setItems(taskList);

        stopButton();

    }
    
    
    /**
     * Stop Time countdown Method
     * @throws SQLServerException 
     */
    private void stopButton() throws SQLServerException {

        Callback<TableColumn<Task, Void>, TableCell<Task, Void>> cellFactory = (TableColumn<Task, Void> param) -> {
            final TableCell<Task, Void> cell = new TableCell<Task, Void>() {
                
                private final Button btn = new Button("Stop");
                
                {
                    btn.setOnAction((ActionEvent event) -> {
                        task = getTableView().getItems().get(getIndex());
                        
                        ThreadExecutor.shutdown();
                        
                        try {
                            bllManager.insertTimeLog(user, project, task, 2);
                        } catch (SQLServerException ex) {
                            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                }
                
                @Override
                public void updateItem(Void item, boolean empty) {
                    btn.getStyleClass().add("btn");
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                    
                }
            };
            return cell;
        };

        colBtn.setCellFactory(cellFactory);

    }
    
    /**
     * Selected task method
     * @param event 
     * @exception SQLServerException
     */

    @FXML
    private void setSelectTask(MouseEvent event) throws SQLServerException {
        startTracker();

    }

  /**
   *  A Method used for counting the time on a task
   * @throws SQLServerException 
   */
    private void startTracker()throws SQLServerException  {

        long startTime = System.currentTimeMillis();
        ThreadExecutor = Executors.newSingleThreadScheduledExecutor();
        ThreadExecutor.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {

                long elapsedTime = System.currentTimeMillis() - startTime;
                long elapsedSeconds = (elapsedTime / 1000) % 60;
                long elapsedMinutes = ((elapsedTime / 1000) / 60) % 60;
                long elapsedHours = (((elapsedTime / 1000) / 60) / 60) % 24;

                timecountlbl.setText(elapsedHours + " : " + elapsedMinutes + " : " + elapsedSeconds);
            });
        }, 1, 1, TimeUnit.SECONDS);

        try {
            bllManager.insertTimeLog(user, project, task, 1);
        } catch (SQLServerException ex) {
            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Add New Task Method
     *
     * @param event
     * @throws SQLServerException
     */
    @FXML
    private void setAddTask(ActionEvent event) throws SQLServerException {

        if (project != null) {
            if (insertTasklbl.getText().equals(initialName) && Descriplbl.getText().equals(initialDescription)) {

            } else {

                if (checkmoney.isSelected() && addtask != null) {
                    bllManager.insertTaskForProject(project, insertTasklbl.getText(), Descriplbl.getText(), 0);
                    initialName = insertTasklbl.getText();
                    initialDescription = Descriplbl.getText();

                    setTaskTableView(project);

                    insertTasklbl.clear();
                    Descriplbl.clear();

                    startTracker();

                } else if (!checkmoney.isSelected() && addtask != null) {
                    bllManager.insertTaskForProject(project, insertTasklbl.getText(), Descriplbl.getText(), 1);
                    initialName = insertTasklbl.getText();
                    initialDescription = Descriplbl.getText();

                    setTaskTableView(project);

                    insertTasklbl.clear();
                    Descriplbl.clear();
                    startTracker();
                }
            }
        }
        tasktableview.getItems().clear();

    }

    /**
     * Menu bar
     *
     * @param event
     */
    @FXML
    private void setMenuPopUp(MouseEvent event) {
        usrmenubar.setVisible(true);

    }

    @FXML
    private void setCloseMenubar(MouseEvent event) {

        usrmenubar.setVisible(false);

    }

    /**
     * Opening Scenes methods
     *
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
