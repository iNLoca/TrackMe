/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;
import trackme.dal.dao.TaskDAO;
import trackme.dal.dao.UserDAO;
import trackme.dal.dao.TimeLoggerDAO;
import trackme.dal.dao.ProjectDAO;
        
/**
 *
 * @author WøbbePC
 */
public class DALManager implements IDALFacade {
    
    private final UserDAO userDAO;
    private final TimeLoggerDAO timeLoggerDAO;
    private final TaskDAO taskDAO;
    private final ProjectDAO projectDAO;
    
    public DALManager() {
        this.userDAO = new UserDAO();
        this.timeLoggerDAO= new TimeLoggerDAO();
        this.taskDAO = new TaskDAO();
        this.projectDAO = new ProjectDAO();
    }
        
    
    @Override
    public User getUser(String email, String password) {
        try {
            return userDAO.getUser(email, password);
        } catch (SQLException ex) {
            Logger.getLogger(DALManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Project> getUserProjectTime() {
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


    @Override
    public void insertTaskForProject(Project project, String name, String description, int toPay) throws SQLServerException {
        taskDAO.insertTask(project, name, description, toPay);
    }

    @Override
    public void insertTimeLog(User user, Project project, Task task, int timeType) throws SQLServerException {
        timeLoggerDAO.insertTimeLog(user, project, task, timeType);
    }

    @Override
    public void getTimeForTask(User user, Task task) throws SQLServerException {
        timeLoggerDAO.getTimeForTask(user, task);
    }

    @Override

    public List<User> getAllUsers() throws SQLServerException {
         return userDAO.getAllUsers();
    }

    @Override
    public List<Task> getAllTaskLogsForProject(User user, Project project) throws SQLServerException {
        return timeLoggerDAO.getAllTaskLogsForProject(user, project);

    }

    @Override
    public void createNewUser(String name, String password, String email, int isAdmin) throws SQLServerException {
         userDAO.createNewUser(name, password, email, isAdmin);
    }

    @Override
    public void addEditUser(User user, String name, String email, String password, int isAdmin) {
        userDAO.addEditUser(user, name, email, password, isAdmin);
    }

    @Override
    public void deleteUser(User user) throws SQLServerException {
        userDAO.deleteUser(user);
    }

    @Override
    public void editTimeLog(User user, Project project, Task task, LocalDateTime startTime, LocalDateTime endTime) throws SQLServerException {
        timeLoggerDAO.editTimeLog(user, project, task, startTime, endTime);
    }

    @Override
    public List<Project> getAllProjects() throws SQLServerException {
        return projectDAO.getAllProjects();
    }

    @Override
    public void createProject(String name, String clientName, String cost) throws SQLServerException {
        projectDAO.createProject(name, clientName, cost);
    }

    @Override
    public void getAllTimeLogsForTask(Task task) throws SQLServerException {
        timeLoggerDAO.getAllTimeLogsForTask(task);
        
    }
}
