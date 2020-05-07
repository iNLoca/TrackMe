/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import trackme.be.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.Project;
import trackme.dal.DBConnectionProvider;

/**
 *
 * @author WøbbePC
 */
public class TaskDAO {
    
    private final DBConnectionProvider connection;
    
    public TaskDAO(){
        connection = new DBConnectionProvider();
    }
    
    
    public void insertTaskForProject(Project project, String name, String description) throws SQLServerException{
    String sql = "INSERT INTO [Task] (taskName, description) VALUES (?,?)";
    
    try(Connection con = connection.getConnection()){
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.executeUpdate();
    }    
    catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
    public List<Task> getTasksForProject(Project project) throws SQLServerException{
    String sql = "SELECT * FROM [Task] JOIN TaskForProject ON TaskForProject.taskId = Task.id WHERE projectId = ?";
    List<Task> tasks = new ArrayList<>();
    
    try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, project.getId());
        
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
        int id = rs.getInt("id");
        String name = rs.getString("taskName");
        String description = rs.getString("description");
        tasks.add(new Task(id, name, description));
        
        }     
    }   catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tasks;
    }
    
}
