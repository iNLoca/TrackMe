/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.User;
import trackme.dal.dao.UserDAO;
        
/**
 *
 * @author WøbbePC
 */
public class DALManager implements IDALFacade {
    
    private final UserDAO userDAO;

    public DALManager() {
        this.userDAO = new UserDAO();
    }
    
    public User getUser(String email, String password) {
        try {
            return userDAO.getUser(email, password);
        } catch (SQLException ex) {
            Logger.getLogger(DALManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
  
    
}
