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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
    private Button logoutbtn;
    @FXML
    private JFXButton overviewbtn;
    @FXML
    private JFXButton trackerbtn;

    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/UserOverview.fxml";

    private ScheduledExecutorService absenceThreadExecutor;
    /**
     * Initializes the controller class.
     */
    private UserModel userModel;
    private TaskModel taskModel;
    private ProjectModel projectModel;
    @FXML
    private Label usrnamelbl;
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
    private JFXButton editbtn;
    @FXML
    private CheckBox checkmoney;
    @FXML
    private JFXButton addtask;
    @FXML
    private TableColumn<Task, Void> colBtn;

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

    private void setTaskTableView(Project project) throws SQLServerException {

        ObservableList<Task> taskList = FXCollections.observableArrayList(bllManager.getTasksForProject(project));
        taskcolmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        desccolm.setCellValueFactory(new PropertyValueFactory<>("description"));
        moneycolmn.setCellValueFactory(new PropertyValueFactory<>("toPay")); //not finnished 

        tasktableview.setItems(taskList);

        addStartButton();
        
        
        
      
    }

    private void addStartButton() throws SQLServerException {

        
        
        Callback<TableColumn<Task, Void>, TableCell<Task, Void>> cellFactory = new Callback<TableColumn<Task, Void>, TableCell<Task, Void>>() {
            @Override
            public TableCell<Task, Void> call(TableColumn<Task, Void> param) {
                final TableCell<Task, Void> cell = new TableCell<Task, Void>() {

                    private final Button btn = new Button("Start");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            task = getTableView().getItems().get(getIndex());
                            // System.out.println("selctedTask: " + task);

                            if (btn != null && !LOl) {
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
                                    bllManager.insertTimeLog(user, project, task, 1);
                                } catch (SQLServerException ex) {
                                    Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }

                    }
                };
                return cell;
            }

        };

        colBtn.setCellFactory(cellFactory);

       
        
    }

    @FXML
    private void setSelectTask(MouseEvent event) {

        introtasklbl.setVisible(true);
        introdeslbl.setVisible(true);

        tasknamelbl.setText(tasktableview.getSelectionModel().getSelectedItem().getName());
        descriptionlbl.setText(tasktableview.getSelectionModel().getSelectedItem().getDescription());

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
    private void PressStop(ActionEvent event) throws SQLServerException {
        LOl = false;
        absenceThreadExecutor.shutdown();
        bllManager.insertTimeLog(user, project, task, 2);
       // absenceThreadExecutor.shutdownNow();
        
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
                    
                } else if (!checkmoney.isSelected() && addtask != null) {
                    bllManager.insertTaskForProject(project, insertTasklbl.getText(), Descriplbl.getText(), 1);
                    initialName = insertTasklbl.getText();
                    initialDescription = Descriplbl.getText();
                    
                     setTaskTableView(project);
                    
                    insertTasklbl.clear();
                    Descriplbl.clear();
                   
                }
            }
        }
        tasktableview.getItems().clear();
      ////NOT DONE
        

    }


}
