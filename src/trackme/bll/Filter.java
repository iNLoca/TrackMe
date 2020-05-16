/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import java.awt.font.TextHitInfo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        for (Task task : tasks) {
            Date date2 = Date.from(task.getTaskTime().get(0).getTime().atZone(ZoneId.systemDefault()).toInstant());
            if(date1.before(date2) && date3.after(date2)){        
        }else{
            tasks.remove(task);}
        }
        return tasks;
    }
    
}