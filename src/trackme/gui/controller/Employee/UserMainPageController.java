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
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
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
    private TableColumn<Task, ImageView> moneycolmn;
    @FXML
    private TableColumn<Task, Void> colBtn;
    @FXML
    private TableColumn<Task, String> totaltimecolmn;

    @FXML
    private JFXButton editbtn;
    @FXML
    private CheckBox checkmoney;
    @FXML
    private JFXButton addtask;

    private UserModel userModel;
    private TaskModel taskModel;
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
    private String ImageURL = "/trackme/gui/icons/yesmoney.png";
    ImageView newimageview = new ImageView(ImageURL);

    private String ImageURL2 = "/trackme/gui/icons/nomoney.png";
    ImageView newimageview2 = new ImageView(ImageURL2);

    ObservableList<Task> taskList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userModel = UserModel.getInstance();
        user = userModel.getLoggedInUser();
        taskModel = TaskModel.getInstance();
        taskModel.getAllProjects();
        usrnamelbl.setText(user.getName());
        this.bllManager = new BLLManager();
        setProjectsInCombobox();

        if (taskModel.getThread() != 0) {
            startTracker();
        }
    }

    /**
     * Project Combo box setup method
     *
     * @param user
     * @throws SQLServerException
     * @throws SQLException
     */
    private void setProjectsInCombobox() {
        ObservableList<Project> projectList = FXCollections.observableArrayList(bllManager.getAllProjects());
        projectbox.getItems().clear();
        projectbox.getItems().addAll(projectList);
        projectbox.getSelectionModel().select(projectbox.getValue());

    }

    @FXML
    private void setSelectedProjects(ActionEvent event) {
        project = projectbox.getSelectionModel().getSelectedItem();
        setTaskTableView(project);

    }

    /**
     * Setup Table Method
     *
     * @param project
     * @throws SQLServerException
     */
    private void setTaskTableView(Project project) {
        List<Task> tempTask = project.getTasks();
        for (Task task1 : tempTask) {
            bllManager.getTotalTimeForTask(task1);
            task1.setTotalTime(bllManager.convertSecondsToHourMinuteSecond(task1));
        }

        this.taskList = FXCollections.observableArrayList(tempTask);
        taskcolmn.setCellValueFactory((cell) -> {
            return cell.getValue().nameProperty();
        });

        desccolm.setCellValueFactory((cell) -> {
            return cell.getValue().descriptionProperty();
        });

        totaltimecolmn.setCellValueFactory((cell) -> {
            return cell.getValue().totalTimeProperty();
        });

        moneycolmn.setCellValueFactory((cell) -> {
            String imageString = "/trackme/gui/icons/yesmoney.png";

            if (cell.getValue().getToPay() == 1) {
                imageString = "/trackme/gui/icons/nomoney.png";
            }

            return new SimpleObjectProperty<>(new ImageView(imageString));
        });

        tasktableview.setItems(taskList);

        stopButton();

    }

    /**
     * Stop Time countdown Method
     *
     * @throws SQLServerException
     */
    private void stopButton() {

        Callback<TableColumn<Task, Void>, TableCell<Task, Void>> cellFactory = (TableColumn<Task, Void> param) -> {
            final TableCell<Task, Void> cell = new TableCell<Task, Void>() {

                private final Button btn = new Button("Stop");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        task = getTableView().getItems().get(getIndex());
                        task = getTableView().getItems().get(getIndex());
                        taskModel.setThread(0);
                        ThreadExecutor.shutdown();

                        bllManager.insertTimeLog(user, project, task, 2);
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
     *
     * @param event
     * @exception SQLServerException
     */
    @FXML
    private void setSelectTask(MouseEvent event) {
        int index = tasktableview.getSelectionModel().getSelectedIndex();
        task = tasktableview.getItems().get(index);
        startTracker();

    }
    long startTime = 0;
    /**
     * A Method used for counting the time on a task
     *
     * @throws SQLServerException
     */
    private void startTracker() {
        if (taskModel.getThread() == 0) {
            startTime = System.currentTimeMillis();
        } else {
            task = taskModel.getCurrentTask();
            startTime = taskModel.getThread();
        }
        ThreadExecutor = Executors.newSingleThreadScheduledExecutor();
        ThreadExecutor.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {

                long elapsedTime = System.currentTimeMillis() - startTime;
                long elapsedSeconds = (elapsedTime / 1000) % 60;
                long elapsedMinutes = ((elapsedTime / 1000) / 60) % 60;
                long elapsedHours = (((elapsedTime / 1000) / 60) / 60) % 24;

                timecountlbl.setText(elapsedHours + " : " + elapsedMinutes + " : " + elapsedSeconds);
            });
        }, 0, 1, TimeUnit.SECONDS);
        bllManager.insertTimeLog(user, project, task, 1);
    }

    /**
     * Add New Task Method
     *
     * @param event
     * @throws SQLServerException
     */
    @FXML
    private void setAddTask(ActionEvent event) {

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
        taskModel.setThread(startTime);
        taskModel.setCurrentTask(task);
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
        taskModel.setThread(startTime);
        taskModel.setCurrentTask(task);
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
         if (ThreadExecutor != null && !ThreadExecutor.isShutdown()) {
            taskModel.setThread(0);
            ThreadExecutor.shutdown();
             bllManager.insertTimeLog(user, project, task, 2);
        }
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
        taskModel.setThread(startTime);
        taskModel.setCurrentTask(task);
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
