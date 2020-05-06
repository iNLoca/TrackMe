/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.be;

import java.time.LocalDateTime;

/**
 *
 * @author WøbbePC
 */
public class Task {
    
    private int id;
    private String name;
    private String description;
    private LocalDateTime overallTime;

    public Task(int id, String name, String description) {
        this.id = id;
        this.description = description;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
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

}
