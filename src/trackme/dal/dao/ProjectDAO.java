/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal.dao;

import trackme.be.Project;
import trackme.dal.DBConnectionProvider;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    public ProjectDAO(){
        connection = new DBConnectionProvider();
    }
    
    public List<Project> getProjectsForUser(User user) throws SQLException{
    String sql = "SELECT * FROM [UserAssignedProject] JOIN Projects ON UserAssignedProject.projectId = Projects.id WHERE userId = ?";
    List<Project> projects = new ArrayList<>();
    
    try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, user.getId());
        
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String client = rs.getString("clientName");
        int cost = rs.getInt("cost");
        // int cost = rs.getInt("type"); (pause or not)
        // fore( project from Projectes)
        /* if(project.id == project.id && date+- same && project type is == 2){
        `remove prom original list
        }
        if(project.type == 1 >Start ) 
          rore(projects)
        
        
        1 Task for each line with 1 timelog
        List<Task> with all timelog for task
        
        fore to loop through list
        type 1 or 2
        if type 1 i++
        if type 2 i--
        
        
        
        
        */
        projects.add(new Project(id, name, client, cost));
        
        }     
    }
        return projects;
    }

}
