/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.model;

import javafx.collections.ObservableList;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;

/**
 *
 * @author mac
 */
interface ITaskModel {
    
   public void getTasksForProject(Project project);
   
   public ObservableList<Task> getTaskList();
   
}
