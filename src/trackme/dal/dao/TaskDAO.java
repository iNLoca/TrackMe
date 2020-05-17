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
import java.sql.Statement;
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
    
    
    public void insertTask(Project project, String name, String description, int toPay) throws SQLServerException{
    String sql = "INSERT INTO [Task] (taskName, description, toPay) VALUES (?,?,?)";
    
    try(Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, toPay);
            pstmt.execute();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            insertTaskForProject(id, project.getId());
    }    
    catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
  

    
    private void insertTaskForProject(int taskId, int projectId) throws SQLServerException {
        String sql = "INSERT INTO [TaskForProject] (taskId, projectId) VALUES (?,?)";
        
         try(Connection con = connection.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(sql)){
             pstmt.setInt(1, taskId);
             pstmt.setInt(2, projectId);
             pstmt.execute();
         }
         catch(SQLException ex) {
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
        int toPay = rs.getInt("toPay");
        tasks.add(new Task(id, name, description, toPay));
        
        }     
    }   catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tasks;
    }
    
}
