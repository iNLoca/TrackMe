/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import trackme.be.Task;

/**
 *
 * @author WøbbePC
 */
public class Filter {
    
    public List<Task> filterList(LocalDate fromTime, LocalDate toTime, List<Task> tasks) throws ParseException{
        Date date1 = Date.from(fromTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date3 = Date.from(toTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
          
        for (int i = 0; i < tasks.size(); i++) {
            Date date2 = Date.from(tasks.get(i).getTaskTime().get(0).getTime().atZone(ZoneId.systemDefault()).toInstant());
            if(date2.before(date1) || date2.after(date3)) {
                tasks.remove(i);
            }
        }
        return tasks;
    }
    
}