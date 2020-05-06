/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;
import trackme.dal.dao.TaskDAO;
import trackme.dal.dao.UserDAO;
import trackme.dal.dao.TimeLoggerDAO;
        
/**
 *
 * @author WøbbePC
 */
public class DALManager implements IDALFacade {
    
    private final UserDAO userDAO;
     private final TimeLoggerDAO timeLoggerDAO;
    private final TaskDAO taskDAO;
    
    public DALManager() {
        this.userDAO = new UserDAO();
        this.timeLoggerDAO= new TimeLoggerDAO();
        this.taskDAO = new TaskDAO();
    }
        
    
    public User getUser(String email, String password) {
        try {
            return userDAO.getUser(email, password);
        } catch (SQLException ex) {
            Logger.getLogger(DALManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Project> getAllProjects() {
        try {
            User us = userDAO.getUser("martinwobbe@email.com", "password");;
            return timeLoggerDAO.getUserProjectTime(us);
                    } catch (SQLException ex) {
            Logger.getLogger(DALManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Task> getTasksForProject(Project project) throws SQLServerException{
            return taskDAO.getTasksForProject(project);
        
    }

    @Override
    public List<Project> getUserProjectTime(User user) throws SQLServerException {
        return timeLoggerDAO.getUserProjectTime(user);
    }
  
    
}
