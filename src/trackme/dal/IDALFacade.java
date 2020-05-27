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
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;

/**
 *
 * @author WøbbePC
 */
public interface IDALFacade {
    
    User getUser(String email, String password);

    public List<User> getAllUsers() throws SQLServerException;
    public List<Project> getUserProjectTime();
    public List<Task> getTasksForProject(Project project) throws SQLServerException;
    public List<Project> getUserProjectTime(User user) throws SQLServerException;
    public List<Project> getProjectsForUser(User user) throws SQLException;
    public void insertTaskForProject(Project project, String name, String description, int toPay) throws SQLServerException;
    public void insertTimeLog(User user, Project project, Task task, int timeType) throws SQLServerException;
    public void getTimeForTask(User user, Task task) throws SQLServerException;
    public List<Task> getAllTaskLogsForProject(User user, Project project) throws SQLServerException;
    public void createNewUser(String name, String password, String email, int isAdmin) throws SQLServerException;
    public void addEditUser(User user, String name, String email, String password, int isAdmin);
    public void deleteUser(User user) throws SQLServerException;
    public void editTimeLog(User user, Project project, Task task, LocalDateTime startTime, LocalDateTime endTime) throws SQLServerException;
    public List<Project> getAllProjects() throws SQLServerException;
    public void createProject(String name, String clientName, String cost) throws SQLServerException;
    public void getAllTimeLogsForTask(Task task) throws SQLServerException;
}
