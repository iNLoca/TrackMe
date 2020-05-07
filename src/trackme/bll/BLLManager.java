/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;
import trackme.dal.DALManager;
import trackme.dal.IDALFacade;

/**
 *
 * @author mac
 */
public class BLLManager implements IBLLFacade {

    private DALManager dalManager;

    public BLLManager() {
        dalManager = new DALManager();
        List<Project> pros = dalManager.getAllProjects();

    }

    @Override
    public User loginUser(String username, String password) {
        return dalManager.getUser(username, password);
    }

    @Override
    public List<Task> getTasksForProject(Project project) throws SQLServerException{
        return dalManager.getTasksForProject(project);
    }

    @Override
    public List<Project> getUserProjectTime(User user) throws SQLServerException {
        return dalManager.getUserProjectTime(user);
    }

    @Override
    public List<Project> getProjectsForUser(User user) throws SQLException {
        return dalManager.getProjectsForUser(user);
    }

    @Override
    public void insertTaskForProject(Project project, String name, String description) throws SQLServerException {
        dalManager.insertTaskForProject(project, name, description);
    }

}
