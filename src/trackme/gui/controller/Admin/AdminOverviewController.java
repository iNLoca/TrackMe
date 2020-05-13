/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author mac
 */
public class AdminOverviewController implements Initializable{

    @FXML
    private AnchorPane OverviewUser;
    @FXML
    private JFXComboBox<?> sortcombobox;
    @FXML
    private BarChart<?, ?> projectBarChart;
    @FXML
    private TableView<?> tasksOverviewTable;
    @FXML
    private TableColumn<?, ?> tasks;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> tamespent;
    @FXML
    private ImageView menubar;
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
    private JFXButton editovbtn;
    
    private final String LoginScene = "/trackme/gui/view/Login.fxml";
    
    
      @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void setSortComboBox(ActionEvent event) {
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
    private void setOverview(ActionEvent event) {
    }

    @FXML
    private void setFrontPage(ActionEvent event) {
    }

  

    @FXML
    private void setEditOV(ActionEvent event) {
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
