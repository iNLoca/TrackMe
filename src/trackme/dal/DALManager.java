/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;
import java.sql.SQLException;
import trackme.be.User;
import trackme.dal.UserDAO;
        
/**
 *
 * @author WøbbePC
 */
public class DALManager implements IDALFacade {
    
    private final UserDAO userDAO;

    public DALManager(UserDAO userDAO) {
        this.userDAO = new UserDAO();
    }
    
    public User getUser(String email, String password) throws SQLException{
        return userDAO.getUser(email, password);
    }
    
}
