/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;
import trackme.bll.IBLLFacade;

/**
 *
 * @author mac
 */
public class ProjectModel implements IProjectModel {
    private final IBLLFacade BLLManager;
    private final ObservableList<Project> projectList =FXCollections.observableArrayList();
    public ProjectModel (IBLLFacade BLLManager) {
        this.BLLManager = BLLManager;
    } 

    
    
    public List<Project> getUserProjectTime(User user) throws SQLServerException {
        return BLLManager.getUserProjectTime(user);
    }

    public void loadAllProjectsForUser(User user)throws SQLException{
    List<Project> allProjects = BLLManager.getProjectsForUser(user);
    projectList.clear();
    projectList.addAll(projectList);
    }
    @Override
    public List<Project> getProjectsForUser(User user)  {
        return projectList;
    }

    
}
