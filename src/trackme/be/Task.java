 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.be;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author WøbbePC
 */
public class Task {
    
    private int id;
    private final StringProperty description = new SimpleStringProperty();
    private List<TimeLog> taskTime = new ArrayList<>();
    private final StringProperty name =  new SimpleStringProperty();
    private final StringProperty date =  new SimpleStringProperty();
    private final StringProperty totalTime =  new SimpleStringProperty();
    private int toPay; //0 = to be paid, 1 = to not be paid
    private long totalTimeInSeconds;

    public Task(int id, String name, String description, int toPay) {
        this.id = id;
        this.description.set(description);
        this.name.set(name);
        this.toPay = toPay;

    }
    
    public String getTotalTime() {
        return totalTime.get();
    }

    public void setTotalTime(String value) {
         totalTime.set(value);
    }
    
    public String getDate() {
        return date.get();
    }

    public void setDate(String value) {
         date.set(value);
    }
    
    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return getName(); 
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
         description.set(value);
    }
    
    public StringProperty descriptionProperty(){
       return description;
    }

    public int getToPay() {
        return toPay;
    }

    public void setToPay(int toPay) {
        this.toPay = toPay;
    }

    public List<TimeLog> getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(List<TimeLog> taskTime) {
        this.taskTime = taskTime;
    }
    
    public void addTime(TimeLog time){
        taskTime.add(time);
    }

    public long getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    public void setTotalTimeInSeconds(long totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
    }
    
    
}
