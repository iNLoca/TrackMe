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
    
    public void createNewUser(String name, String password, String email, int isAdmin){
    String sql = "INSERT INTO [User](name, email, isAdmin, password) VALUES (?,?,?,?)";
    
    try(Connection con = connection.getConnection()){
    PreparedStatement pstmt = con.prepareStatement(sql);
    pstmt.setString(1, name);
    pstmt.setString(2, email);
    pstmt.setInt(3, isAdmin);
    pstmt.setString(4, password);
    pstmt.executeQuery();
    
    }   catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User getUser(String email, String password){
       
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
        } catch (SQLException ex) { 
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM [User]";

        try(Connection con = connection.getConnection()){
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int isAdmin = rs.getInt("isAdmin");
        String email = rs.getString("email");
        String password = rs.getString("password");
        
        if(isAdmin==1){
            User tempUser = new User(id, name, User.UserType.ADMIN);
            tempUser.setEmail(email);
            tempUser.setPassword(password);
            users.add(tempUser);
        }
        else {
            User tempUser = new User(id, name, User.UserType.EMPLOYEE);
            tempUser.setEmail(email);
            tempUser.setPassword(password);
            users.add(tempUser);
        }
        }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }


    
        public void addEditUser(User user, String name, String email, String password, int isAdmin) {
        String sql = "UPDATE [User] SET name = ?, email = ?, isAdmin = ?, password = ? WHERE id = ? ";
        
        try (Connection con = connection.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2,email); 
            pstmt.setInt(3, isAdmin);
            pstmt.setString(4, password);
            pstmt.setInt(5, user.getId());
            pstmt.executeUpdate();

        } catch (SQLServerException sqlse) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, sqlse);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

        public void deleteUser(User user){
        String sql = "DELETE  FROM [User] WHERE id=?";
        
        try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, user.getId());
        pstmt.executeQuery();
        
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

}
