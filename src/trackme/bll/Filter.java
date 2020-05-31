/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import trackme.be.Task;

/**
 *
 * @author WøbbePC
 */
public class Filter {
    
    public List<Task> filterList(LocalDate fromTime, LocalDate toTime, List<Task> tasks){
        
        Date startTime = Date.from(fromTime.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
        Date endTime = Date.from(toTime.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
        
        List<Task> includedTasks = new ArrayList<>();  
        for (int i = 0; i < tasks.size(); i++) {
            Date taskDate = Date.from(tasks.get(i).getTaskTime().get(0).getTime().atZone(ZoneId.systemDefault()).toInstant());
            if(!taskDate.before(startTime) && !taskDate.after(endTime)) {
                includedTasks.add(tasks.get(i));
            }
                
        }
        return includedTasks;
    }
    
}