/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller.Employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
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
import trackme.be.Task;
import trackme.be.User;
import trackme.bll.BLLManager;
import trackme.gui.model.UserModel;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class UserOverviewController implements Initializable {

    @FXML
    private AnchorPane OverviewUser;
    @FXML
    private JFXComboBox<Project> sortcombobox;
    @FXML
    private TableView<Task> tasksOverviewTable;
    @FXML
    private TableColumn<Task, String> tasks;
    @FXML
    private TableColumn<Task, LocalDate> date;
    @FXML
    private TableColumn<Task, Integer> tamespent;
    @FXML
    private Pane usrmenubar;
    @FXML
    private Button logoutbtn;
    @FXML
    private ImageView menubar;
    @FXML
    private JFXButton overviewbtn;
    @FXML
    private JFXButton trackerbtn;
    @FXML
    private Label usrnamelbl;
    @FXML
    private JFXButton editovbtn;
    @FXML
    private BarChart<String, Integer> projectBarChart;
    @FXML
    private JFXDatePicker fromDatePicker;
    @FXML
    private JFXDatePicker toDatePicker;

    private User user;
    private Project project;
    private List<Project> projects;
    private UserModel userModel;
    private BLLManager bllManager;
    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/UserOverview.fxml";
    private final String Tracker = "/trackme/gui/view/UserMainPage.fxml";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userModel = UserModel.getInstance();
        user = userModel.getLoggedInUser();
        usrnamelbl.setText(user.getName());
        bllManager = new BLLManager();

        projects = bllManager.getUserProjectTime(user);
        bllManager.getTotalTimeForEachProject(projects);
        setTaskInComboBox();
        setBarChartForSelectedProject(projects);
    }

    /**
     * Task Combo Box setup
     *
     * @param user
     * @throws SQLServerException
     * @throws SQLException
     */
    private void setTaskInComboBox(){
        ObservableList<Project> projectList = FXCollections.observableArrayList(bllManager.getAllProjects());
        sortcombobox.getItems().clear();
        sortcombobox.getItems().addAll(projectList);
        sortcombobox.getSelectionModel().select(sortcombobox.getValue());
    }

    /**
     * Sort combo box on selection
     *
     * @param event
     * @throws SQLServerException
     * @throws ParseException
     */
    @FXML
    private void setSortComboBox(ActionEvent event) {
        project = sortcombobox.getSelectionModel().getSelectedItem();
        setTaskOverview(user, project);
    }

    /**
     * BarChart setup method
     *
     * @param projects
     */
    private void setBarChartForSelectedProject(List<Project> projects) {
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("projects");
        for (Project var : projects) {
            String name = var.getName();
            long time = var.getTotalTimeInSeconds() / 3600;
            dataSeries1.getData().add(new XYChart.Data(name, time));
        }
        projectBarChart.setAnimated(false);
        projectBarChart.setTitle("Overall Time Spent On Projects");
        projectBarChart.getData().add(dataSeries1);
    }

    /**
     * Task Overview table setup method??
     *
     * @param user
     * @param project
     * @throws SQLServerException
     * @throws ParseException
     */
    private void setTaskOverview(User user, Project project){
        List<Task> allTaskLogs = bllManager.getAllTaskLogsForProject(user, project);

        if (fromDatePicker.getValue() == null && toDatePicker.getValue() == null) {
            for (Task task : allTaskLogs) {
                bllManager.getTotalTimeForTask(task);
                task.setDate(task.getTaskTime().get(0).getTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                task.setTotalTime(convertSecondsToHourMinuteSecond(task));
            }
            ObservableList<Task> taskList = FXCollections.observableArrayList(allTaskLogs);
            tasks.setCellValueFactory(new PropertyValueFactory<>("name"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            tamespent.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
            tasksOverviewTable.setItems(taskList);
        } else {
            List<Task> filteredList = bllManager.filterList(fromDatePicker.getValue(), toDatePicker.getValue(), allTaskLogs);
            for (Task task : filteredList) {
                bllManager.getTotalTimeForTask(task);
                task.setDate(task.getTaskTime().get(0).getTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                task.setTotalTime(convertSecondsToHourMinuteSecond(task));
            }
            ObservableList<Task> taskList = FXCollections.observableArrayList(filteredList);
            tasks.setCellValueFactory(new PropertyValueFactory<>("name"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            tamespent.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
            tasksOverviewTable.setItems(taskList);
        }
    }

    /**
     * Time Converter method
     *
     * @param task
     * @return
     */
    private String convertSecondsToHourMinuteSecond(Task task) {
        String time = "";
        int p1 = (int) task.getTotalTimeInSeconds() / 3600;
        int remainder = (int) task.getTotalTimeInSeconds() - p1 * 3600;
        int p2 = remainder / 60;
        remainder = remainder - p2 * 60;
        int p3 = remainder;
        time = p1 + ":" + p2 + ":" + p3;
        return time;
    }

    /**
     * DatePicker Selection Form Date Method
     * @param event
     * @throws SQLServerException
     * @throws ParseException
     */
    @FXML
    private void setSelectedFromDate(ActionEvent event) {
        if (toDatePicker.getValue() == null) {

        } else {
            setTaskOverview(user, project);
        }
    }

    /**
     *DatePicker Selection To Date Method
     * @param event
     * @throws SQLServerException
     * @throws ParseException
     */
    @FXML
    private void setSelectedTODATE(ActionEvent event)  {
        if (fromDatePicker.getValue() == null) {
            
        } else {
            setTaskOverview(user, project);
        }
    }

    /**
     * Menu bar method
     *
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
     * Opening scenes methods
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
        FXMLLoader fxloader = new FXMLLoader(getClass().getResource(Tracker));
        Parent root = fxloader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
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
    private void setEditOV(ActionEvent event) throws IOException {
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
        closePreviousScene = (Stage) editovbtn.getScene().getWindow();
        closePreviousScene.close();
    }

}
