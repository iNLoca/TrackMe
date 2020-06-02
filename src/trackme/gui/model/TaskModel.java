/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.gui.model;

import java.util.List;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.TimeLog;
import trackme.bll.BLLManager;

/**
 *
 * @author WøbbePC
 */
public class TaskModel {

    private static TaskModel single_instance = null;

    /**
     * Static method to create instance of Singleton class
     *
     * @return
     */
    
    private List<Project> listOfProjects;
    private BLLManager bllManager;
    private Task currentTask = null;
    private long threadInMain = 0;
    
    public static TaskModel getInstance() {
        if (single_instance == null) {
            single_instance = new TaskModel();
        }

        return single_instance;
    }

    public TaskModel(BLLManager bllManager) {
        this.bllManager = bllManager;
    }


    public TaskModel() {
        bllManager = new BLLManager();
    }
    
    public List<Project> getAllProjects(){
        listOfProjects = bllManager.getAllProjects();
        return listOfProjects;
    }
    
    public List<Project> getAllCProjects(){
        return listOfProjects;
    }
    
    public long getThread(){
        return threadInMain;
    }
    
    public void setThread(long newvalue){
        threadInMain = newvalue;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
   
   
}
