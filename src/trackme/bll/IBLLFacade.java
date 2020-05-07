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

    public List<Task> getTasksForProject(Project project)throws SQLServerException;
    public List<Project> getUserProjectTime(User user) throws SQLServerException;
    public List<Project> getProjectsForUser(User user) throws SQLException;
}
