/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;

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
/**
 *
 * @author WøbbePC
 */
public class ProjectDAO {
    
    private final DBConnectionProvider connection;
    
    public ProjectDAO(){
        connection = new DBConnectionProvider();
    }
    
    public List getProjectsForUser(int userId) throws SQLException{
    String sql = "SELECT * FROM [Projects] WHERE id = ?";
    List<Project> projects = new ArrayList<>();
    
    try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, userId);
        
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String client = rs.getString("client");
        int cost = rs.getInt("cost");
        projects.add(new Project(id, name, client, cost));
        
        }     
    }
        return projects;
    }

}
