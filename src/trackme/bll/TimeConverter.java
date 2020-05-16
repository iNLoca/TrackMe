/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
    
    public void getTotalTimeForTask(Task task) throws SQLServerException{
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
    public List<LocalDateTime> calculateTime(LocalTime startTime, LocalTime endTime, LocalDate date){
        List<LocalDateTime> newTimes = new ArrayList();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()));
        Date tempDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        cal.setTime(tempDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Instant instant1 = startTime.atDate(LocalDate.of(year, month, day)).atZone(ZoneId.systemDefault()).toInstant();
        Instant instant2 = endTime.atDate(LocalDate.of(year, month, day)).atZone(ZoneId.systemDefault()).toInstant();
        LocalDateTime ldt1 = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault());
        LocalDateTime ldt2 = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault());
        newTimes.add(ldt1);
        newTimes.add(ldt2);
    return newTimes;
    }
    
}
