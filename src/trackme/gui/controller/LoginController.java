/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import trackme.be.User;
import trackme.gui.model.UserModel;

/**
 * FXML Controller class
 *
 * @author mac
 *
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane LoginStage;
    @FXML
    private Button loginbtn;
    @FXML
    private JFXTextField emaillbl;
    @FXML
    private JFXPasswordField passlbl;

    private final String UserLogin = "/trackme/gui/view/UserMainPage.fxml";

    private UserModel userModel;
    @FXML
    private Label errorlbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userModel = UserModel.getInstance();
    }

    private void loadUpNextView(User us) throws IOException {

        if (us.getType() == User.UserType.ADMIN) {
            FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/trackme/gui/view/UserMainPage.fxml"));
            Parent root = fxmloader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            closeLoginScene();
        } else {
            FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/trackme/gui/view/UserMainPage.fxml"));
            Parent root = fxmloader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            closeLoginScene();
        }
    }

    @FXML
    private void pressEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
     String username = emaillbl.getText();
    String password = passlbl.getText();
    
        if (checkifDataIsInserted(username,password)) {
            User us = userModel.loginUser(username,password);
            loadUpNextView(us);
        } else {
            errorlbl.setVisible(true);//Display missing data lol
        }
        }
    }

    @FXML
    private void clickLogIn(ActionEvent event) throws IOException {
     String username = emaillbl.getText();
    String password = passlbl.getText();
    
        if (checkifDataIsInserted(username,password)) {
            User us = userModel.loginUser(username,password);
            loadUpNextView(us);
        } else {
                        errorlbl.setVisible(true);//Display missing data lol

            //Display missing data lol
        }

    }

    private void closeLoginScene() {
        Stage loginStage;
        loginStage = (Stage) loginbtn.getScene().getWindow();
        loginStage.close();
    }

    private boolean checkifDataIsInserted(String username, String password) {
    
    return true;
    }
}
