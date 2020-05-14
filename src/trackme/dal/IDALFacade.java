/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;

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
public interface IDALFacade {
    
    User getUser(String email, String password);

    public List<User> getAllUsers() throws SQLServerException;
    public List<Project> getAllProjects();
    public List<Task> getTasksForProject(Project project) throws SQLServerException;
    public List<Project> getUserProjectTime(User user) throws SQLServerException;
    public List<Project> getProjectsForUser(User user) throws SQLException;
    public void insertTaskForProject(Project project, String name, String description, int toPay) throws SQLServerException;
    public void insertTimeLog(User user, Project project, Task task, int timeType) throws SQLServerException;
    public void getTimeForTask(User user, Task task) throws SQLServerException;
}
