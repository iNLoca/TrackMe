/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;

import java.sql.SQLException;
import trackme.be.User;

/**
 *
 * @author WøbbePC
 */
public interface IDALFacade {
    
    User getUser(String email, String password);
    
}
