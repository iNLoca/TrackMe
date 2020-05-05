/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import trackme.be.User;

/**
 *
 * @author WøbbePC
 */
public interface IBLLFacade {

    public User loginUser(String username, String password);

}
