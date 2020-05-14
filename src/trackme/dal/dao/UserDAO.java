/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Statement;
import trackme.be.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.dal.DBConnectionProvider;

/**
 *
 * @author mac
 */
public class UserDAO {
    
    private final DBConnectionProvider connection;
    
    public UserDAO(){
        connection = new DBConnectionProvider();
    }
    
    public User getUser(String email, String password) throws SQLException{
       
        String sql = "SELECT * FROM [User] WHERE email = ? AND password = ?";

        try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, email);
        pstmt.setString(2, password);

        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int isAdmin = rs.getInt("isAdmin");

        if(isAdmin==1){
            return new User(id, name, User.UserType.ADMIN);
        }
        else {
            return new User(id, name, User.UserType.EMPLOYEE);
        }

        } catch (SQLServerException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
        
    }
    
    public List<User> getAllUsers() throws SQLServerException{
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM [User]";

        try(Connection con = connection.getConnection()){
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);


        
        while(rs.next()){
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int isAdmin = rs.getInt("isAdmin");

        if(isAdmin==1){
            users.add(new User(id, name, User.UserType.ADMIN));
        }
        else {
            users.add(new User(id, name, User.UserType.EMPLOYEE));
        }
        }



        

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }



    

}
