/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;

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

/**
 *
 * @author WøbbePC
 */
public class TaskDAO {
    
    private final DBConnectionProvider connection;
    
    public TaskDAO(){
        connection = new DBConnectionProvider();
    }
    
    public void addNewTask(Project project) throws SQLServerException{
    String sql = ""; 
    //needs to get inserted to task but also into connection table between projects and task
    //need transaction?
    }
    
    
    
    
    public List<Task> getTasksForProject(Project project) throws SQLServerException{
    String sql = "SELECT * FROM [Task] join TaskForProject ON TaskForProject.taskId = Task.id WHERE projectId = ?";
    List<Task> tasks = new ArrayList<>();
    
    try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, project.getId());
        
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
        int id = rs.getInt("id");
        String description = rs.getString("description");
        tasks.add(new Task(id, description));
        
        }     
    }   catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tasks;
    }
    
}
