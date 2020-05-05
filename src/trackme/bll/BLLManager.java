/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.User;
import trackme.dal.DALManager;
import trackme.dal.IDALFacade;

/**
 *
 * @author mac
 */
public class BLLManager implements IBLLFacade{
    
    private IDALFacade dalFacade;

    public BLLManager() {
        dalFacade= new DALManager();
    }

    
    @Override
    public User loginUser(String username, String password) {
       return dalFacade.getUser(username,password);
    }

}
