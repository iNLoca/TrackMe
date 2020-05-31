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

    public List<User> getAllUsers();
    public List<Task> getTasksForProject(Project project);
    public List<Project> getUserProjectTime(User user) ;
    public void insertTaskForProject(Project project, String name, String description, int toPay);
    public void insertTimeLog(User user, Project project, Task task, int timeType);
    public void getTimeForTask(User user, Task task) ;
    public List<Task> getAllTaskLogsForProject(User user, Project project) ;
    public void createNewUser(String name, String password, String email, int isAdmin);
    public void addEditUser(User user, String name, String email, String password, int isAdmin);
    public void deleteUser(User user);
    public void editTimeLog(User user, Project project, Task task, LocalDateTime startTime, LocalDateTime endTime);
    public List<Project> getAllProjects();
    public void createProject(String name, String clientName, String cost);
    public void getAllTimeLogsForTask(Task task);
}
