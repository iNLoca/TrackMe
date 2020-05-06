/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.List;
import trackme.be.Project;
import trackme.be.User;

/**
 *
 * @author mac
 */
public interface IProjectModel {
    
    public List<Project> getUserProjectTime(User user) throws SQLServerException;
    
}
