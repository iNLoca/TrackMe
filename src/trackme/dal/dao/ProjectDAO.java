/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal.dao;

import trackme.be.Project;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.User;
import trackme.dal.DBConnectionProvider;
/**
 *
 * @author WøbbePC
 */
public class ProjectDAO {
    
    private final DBConnectionProvider connection;
    private TaskDAO taskDAO;
    public ProjectDAO(){
        taskDAO = new TaskDAO();
        connection = new DBConnectionProvider();
    }
    

    public List<Project> getAllProjects(){
        List<Project> allProjects = new ArrayList();
        String sql = "SELECT * FROM [Projects]";
        try(Connection con = connection.getConnection()){
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String client = rs.getString("clientName");
        int cost = rs.getInt("cost");
        Project proj  = new Project(id, name, client, cost);
        proj.setTaskForProject(taskDAO.getTasksForProject(proj)); 
        allProjects.add(proj);
        }

    }   catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return allProjects;
    }
    
    public void createProject(String name, String clientName, String cost){
    String sql = "INSERT INTO [Projects] (name, clientName, cost) VALUES (?,?,?)";

    try(Connection con = connection.getConnection()){
    PreparedStatement pstmt = con.prepareStatement(sql);
    pstmt.setString(1, name);
    pstmt.setString(2, clientName);
    pstmt.setInt(3, Integer.valueOf(cost));
    pstmt.executeQuery();

    
    }   catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
