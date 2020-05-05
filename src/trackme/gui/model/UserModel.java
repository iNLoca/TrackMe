/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.model;

import trackme.be.User;
import trackme.bll.BLLManager;
import trackme.bll.IBLLFacade;

/**
 *
 * @author mac
 */
public class UserModel {

    private static UserModel single_instance = null;
    
    private User loggedInUser;

    // static method to create instance of Singleton class 
    public static UserModel getInstance() {
        if (single_instance == null) {
            single_instance = new UserModel();
        }

        return single_instance;
    }

    private IBLLFacade facade;
    
    public UserModel() {
        facade= new BLLManager();
    }
    
    
    public User loginUser(String username, String password){
        loggedInUser = facade.loginUser(username,password);
        return loggedInUser;
    }
    
    public User getLoggedInUser(){
        return loggedInUser;
    }
    
}
