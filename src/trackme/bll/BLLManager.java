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

    private IDALFacade dalFacade;
    private TimeConverter conv;

    public BLLManager() {
        dalFacade = new DALManager();
        List<Project> pros = dalFacade.getAllProjects();
        conv = new TimeConverter();
        conv.getProjectTime(pros);
    }

    @Override
    public User loginUser(String username, String password) {
        return dalFacade.getUser(username, password);
    }

    @Override
    public List<Task> getTasksForProject(Project project) throws SQLServerException{
        return dalFacade.getTasksForProject(project);
    }

    @Override
    public List<Project> getUserProjectTime(User user) throws SQLServerException {
        return dalFacade.getUserProjectTime(user);
    }

}
