/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;

import java.sql.SQLException;
import java.util.List;
import trackme.be.Project;
import trackme.be.User;

/**
 *
 * @author WøbbePC
 */
public interface IDALFacade {
    
    User getUser(String email, String password);

    public List<Project> getAllProjects();
    
}
