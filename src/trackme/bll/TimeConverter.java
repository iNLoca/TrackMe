/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.TimeLog;
import trackme.be.User;
import trackme.dal.DALManager;
/**
 *
 * @author WøbbePC
 */
public class TimeConverter {
    private DALManager dalManager;

    public TimeConverter() {
        dalManager = new DALManager();
    }
    
    
    
    public void getProjectTime(List<Project> projects){

        getTotalTimeForEachProject(projects);
        
          for (Project project : projects) {
              System.out.println("Project :" +project.getName() + " Lol " + project.getTotalTimeInSeconds());
        }
//        find time spent on task in one update?
    }
    
    public void getTotalTimeForTask(User user, Task task) throws SQLServerException{
        dalManager.getTimeForTask(user, task);
        List<TimeLog> variable1 = task.getTaskTime();
          long variable2 = 0;
          long variableToDelete = 0;
          long variableToDelete2 = 0; 
          ZoneId zoneId = ZoneId.systemDefault(); 
            for (int i = 0; i < variable1.size(); i++) {
              if(i%2 == 1){
                 variableToDelete = variable1.get(i).getTime().atZone(zoneId).toEpochSecond();
                 variable2 += variableToDelete - variableToDelete2;
              }else{
                  variableToDelete2 = variable1.get(i).getTime().atZone(zoneId).toEpochSecond();
              }
            }
            task.setTotalTimeInSeconds(variable2);
    }
    
    public void  getTotalTimeForEachProject(List<Project> projects){
        for (Project project : projects) {
          List<TimeLog> variable1 =  project.getProjectTime();
          long variable2 = 0;
          long variableToDelete = 0;
          long variableToDelete2 = 0; 
          ZoneId zoneId = ZoneId.systemDefault(); 
            for (int i = 0; i < variable1.size(); i++) {
              if(i%2 == 1){
                 variableToDelete = variable1.get(i).getTime().atZone(zoneId).toEpochSecond();
                 variable2 += variableToDelete - variableToDelete2;
              }else{
                  variableToDelete2 = variable1.get(i).getTime().atZone(zoneId).toEpochSecond();
              }
            }

            project.setTotalTimeInSeconds(variable2);
        }
    }

    
}
