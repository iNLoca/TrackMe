/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.List;
import javafx.collections.ObservableList;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;

/**
 *
 * @author mac
 */
interface ITaskModel {
    
   public List<Task> getTasksForProject(Project project)throws SQLServerException;
   
   public ObservableList<Task> getTaskList();
   
}
