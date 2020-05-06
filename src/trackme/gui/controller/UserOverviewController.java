/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import trackme.be.User;
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
    private JFXComboBox<?> sortcombobox;
    @FXML
    private TableView<?> tasksOverviewTable;
    @FXML
    private TableColumn<?, ?> tasks;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> tamespent;
    @FXML
    private Pane usrmenubar;
    @FXML
    private Button logoutbtn;

    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    private final String OverviewScene = "/trackme/gui/view/OverviewScene";
    private final String Tracker = "/trackme/gui/view/UserMainPage.fxml";
    @FXML
    private ImageView menubar;
    @FXML
    private JFXButton overviewbtn;
    @FXML
    private JFXButton trackerbtn;

    /**
     * Initializes the controller class.
     */
    private UserModel userModel;
    @FXML
    private Label usrnamelbl;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       userModel = UserModel.getInstance();
        User us = userModel.getLoggedInUser();
        usrnamelbl.setText(us.getName());
    }

    @FXML
    private void setSortComboBox(ActionEvent event) {
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
    private void setShowMenubar(MouseEvent event) {
        usrmenubar.setVisible(true);

    }

    @FXML
    private void setCloseMenubar(MouseEvent event) {
        usrmenubar.setVisible(false);

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
