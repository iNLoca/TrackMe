/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;
import trackme.bll.IBLLFacade;

/**
 *
 * @author mac
 */
public class TaskModel implements ITaskModel {
    
    private final IBLLFacade BLLManager;
    private final ObservableList<Task>taskList = FXCollections.observableArrayList();
    
    public TaskModel(IBLLFacade BLLManager) {
        this.BLLManager = BLLManager;
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return taskList;
    }

    @Override
    public void getTasksForProject(Project project) { //loadTasks
  //       List<Task> allTasks = BLLManager.getTasksForProject(project);
        taskList.clear();
  //      taskList.addAll(allTasks);
    }
}
