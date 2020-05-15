/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import java.util.List;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;

/**
 *
 * @author WøbbePC
 */
public interface IBLLFacade {

    public User loginUser(String username, String password);
    
    public List<User> getAllUsers() throws SQLServerException;
    public List<Task> getTasksForProject(Project project)throws SQLServerException;
    public List<Project> getUserProjectTime(User user) throws SQLServerException;
    public List<Project> getProjectsForUser(User user) throws SQLException;
    public void insertTaskForProject(Project project, String name, String description, int toPay) throws SQLServerException;
    public void insertTimeLog(User user, Project project, Task task, int timeType) throws SQLServerException;
    public void getTimeForTask(User user, Task task) throws SQLServerException;
    public void getTotalTimeForTask(Task task) throws SQLServerException;
    public void  getTotalTimeForEachProject(List<Project> projects);
    public List<Task> getAllTaskLogsForProject(User user, Project project) throws SQLServerException;
    public void createNewUser(String name, String password, String email, int isAdmin) throws SQLServerException;
    public void addEditUser(User user, String name, String email, String password, int isAdmin);
    public void deleteUser(User user) throws SQLServerException;
    public List<Project> getAllProjects() throws SQLServerException;
    public void createProject(String name, String clientName, String cost) throws SQLServerException;
}
