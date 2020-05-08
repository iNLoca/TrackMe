 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.be;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author WøbbePC
 */
public class Task {
    
    private int id;
//    private String name;
    private String description;
    private LocalDateTime overallTime;
    private final StringProperty name =  new SimpleStringProperty();
    private int toPay; //0 = to be paid, 1 = to not be paid

    public Task(int id, String name, String description, int toPay) {
        this.id = id;
        this.description = description;
        this.name.set(name);
        this.toPay = toPay;

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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return overallTime;
    }

    public void setTime(LocalDateTime time) {
        this.overallTime = time;
    }

    public int getToPay() {
        return toPay;
    }

    public void setToPay(int toPay) {
        this.toPay = toPay;
    }

    
}
