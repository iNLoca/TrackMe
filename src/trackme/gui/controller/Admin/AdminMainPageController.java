/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller.Admin;

import trackme.gui.controller.Employee.UserMainPageController;
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
import trackme.gui.model.UserModel;

/**
 *
 * @author mac
 */
public class AdminMainPageController implements Initializable {

    @FXML
    private AnchorPane adminfrontPane;

    @FXML
    private Pane usrmenubar;
    @FXML
    private Label usrnamelbl;
    @FXML
    private JFXButton overviewbtn;
    @FXML
    private JFXButton trackerbtn;
    @FXML
    private JFXButton createbtn;
    @FXML
    private JFXButton profilesbtn;
    @FXML
    private Button logoutbtn;

    @FXML
    private Label timecountlbl;
    @FXML
    private JFXComboBox<Project> projectbox;

    @FXML
    private TableView<Task> tasktableview;
    @FXML
    private TableColumn<Task, String> taskcolmn;
    @FXML
    private TableColumn<Task, String> desccolm;
    @FXML
    private TableColumn<Task, ImageView> moneycolmn;
    @FXML
    TableColumn<Task, Void> colBtn;
    @FXML
    private TableColumn<?, ?> totaltimespentcolmn;

    @FXML
    private ImageView showup; //Whats that? 
    @FXML
    private CheckBox checkmoney;
    @FXML
    private JFXTextField insertTasklbl;
    @FXML
    private JFXTextField Descriplbl;
    @FXML
    private JFXButton addTask;

    private User user;
    private UserModel userModel;
    private BLLManager bllManager;
    private Project project;
    private Task task;
    private Label tasknamelbl;
    private Label descriptionlbl;
    private Label introtasklbl;   //<- Those are not used at all ? 
    private Label introdeslbl;
    private String initialName;
    private String initialDescription;


    private String ImageURL = "/trackme/gui/icons/yesmoney.png";
    ImageView newimageview = new ImageView(ImageURL);

    private String ImageURL2 = "/trackme/gui/icons/nomoney.png";
    ImageView newimageview2 = new ImageView(ImageURL2);


    ObservableList<Task> taskList;

    private ScheduledExecutorService ThreadExecutor;

   
    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/AdminOverview.fxml";


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userModel = UserModel.getInstance();
        this.user = userModel.getLoggedInUser();
        usrnamelbl.setText(user.getName());
        this.bllManager = new BLLManager();

        try {
            setProjectsInCombobox(user);
        } catch (SQLServerException ex) {
            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("not working");
        } catch (SQLException ex) {
            Logger.getLogger(UserMainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * A method which for Combo Box functionality
     *
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
        refreshTable();
    }
    
    private Callback<TableColumn<Task, Integer>, TableCell<Task, Integer>> getCustomCellFactory() {
        return (TableColumn<Task, Integer> param)
                -> {
            return new TableCell<Task, Integer>() {
                @Override
                public void updateItem(final Integer item, boolean empty) {
                    if (item != null) {
                        if (item == 0) {
                            //this.getChildren().clear();
                            this.getChildren().add(newimageview);
                        } else {
                            this.getChildren().add(newimageview);
                        }
                    }
                }
            };
        };
    }

    /**
     * Method for table functionality
     *
     * @param project
     * @throws SQLServerException
     */
    private void setTaskTableView(Project project) throws SQLServerException {

        this.taskList = FXCollections.observableArrayList(bllManager.getTasksForProject(project));
        taskcolmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        desccolm.setCellValueFactory(new PropertyValueFactory<>("description"));


        // moneycolmn.setCellValueFactory(new PropertyValueFactory<>("toPay")); //not finnished 
        moneycolmn.setCellValueFactory(new PropertyValueFactory<>("toPayImage")); //not finnished 
        //moneycolmn.setCellFactory(getCustomCellFactory());

     


        tasktableview.setItems(taskList);

        stopButton();

    }

    /**
     * Method for stopping the time tracking on a task from // NEEDS REFACTORING
     * the created table cell
     *
     * @throws SQLServerException
     */
    private void stopButton() throws SQLServerException {


        Callback<TableColumn<Task, Void>, TableCell<Task, Void>> cellFactory = new Callback<TableColumn<Task, Void>, TableCell<Task, Void>>() {
            @Override
            public TableCell<Task, Void> call(TableColumn<Task, Void> param) {
                final TableCell<Task, Void> cell = new TableCell<Task, Void>() {

                    private final Button btn = new Button("Stop");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            task = getTableView().getItems().get(getIndex());

                
                            ThreadExecutor.shutdown();
                            try {
                                bllManager.insertTimeLog(user, project, task, 2);
                            } catch (SQLServerException ex) {
                                Logger.getLogger(AdminMainPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                    }
                    String ImageSource = "/trackme/gui/icons/play.png";
                    ImageView imageview = new ImageView(ImageSource);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        // btn.getStyleClass().add("btn");

                        imageview.setFitHeight(30);
                        imageview.setFitWidth(30);

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            imageview.setImage(new Image(ImageSource));
                        } else {
                            setGraphic(imageview);
                            setGraphic(btn);

                        }

                    }

                };

                return cell;

            }

        };

        colBtn.setCellFactory(cellFactory);

    }

    /**
     * Method for starting task tracker after selection  ///NEEDS CHANGES
     *
     * @param event
     * @throws SQLServerException
     * @exception NullPointerException
     */
    @FXML
    private void setSelectTask(MouseEvent event) throws SQLServerException {

        startTracker();

  // Gives nullpointers because those shouldnt be emppty and here
        try {
            tasknamelbl.setText(tasktableview.getSelectionModel().getSelectedItem().getName());
        } catch (NullPointerException e) {
            System.out.println("taskname cannot be null");
        }
        try {
            descriptionlbl.setText(tasktableview.getSelectionModel().getSelectedItem().getDescription());
        } catch (NullPointerException e) {
            System.out.println("description Cannot be null");
        }
    }

    /**
     * A Method used for counting the time on a task
     *
     * @throws SQLServerException
     */
    private void startTracker() throws SQLServerException {

        // LOl = true;
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
     * Refreshing table
     *
     * @throws SQLServerException
     */
    public void refreshTable() throws SQLServerException {
        tasktableview.getItems().removeAll(taskList);
        setTaskTableView(project);
    }

    /**
     * Method for creating a new task /NEEDS PICTURE!!!!
     *
     * @param event
     * @throws SQLServerException
     */
    @FXML
    private void setAddTask(ActionEvent event) throws SQLServerException {
 if (project != null) {
            if (insertTasklbl.getText().equals(initialName) && Descriplbl.getText().equals(initialDescription)) {

            } else {

                if (checkmoney.isSelected() && addTask != null) {
                    initialName = insertTasklbl.getText();
                    initialDescription = Descriplbl.getText();
                    bllManager.insertTaskForProject(project, insertTasklbl.getText(), Descriplbl.getText(), 0);

                    setTaskTableView(project);

                    insertTasklbl.clear();
                    Descriplbl.clear();
                    tasktableview.refresh();

                    //   moneycolmn.setCellFactory((Callback<TableColumn<Task, Integer>, TableCell<Task, Integer>>) newimageview);
                    // moneycolmn.getColumn().add(newimageview);
                    startTracker();

                } else if (!checkmoney.isSelected() && addTask != null) {
                    initialName = insertTasklbl.getText();
                    initialDescription = Descriplbl.getText();
                    bllManager.insertTaskForProject(project, insertTasklbl.getText(), Descriplbl.getText(), 1);

                    setTaskTableView(project);

                    insertTasklbl.clear();
                    Descriplbl.clear();
                    tasktableview.refresh();

                    // moneycolmn.setGraphic(newimageview2);
                    startTracker();
                }
            }
        }

    }

    
   

        
    /**
     * Methods for accessing menu bar
     *
     * @param event
     *
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
     * Opening Scene Methods
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
    private void setCreate(ActionEvent event) throws IOException {

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
    private void setProfiles(ActionEvent event) throws IOException {
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
