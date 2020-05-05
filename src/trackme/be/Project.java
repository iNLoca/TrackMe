/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.be;

import java.sql.Time;
import java.util.List;
import trackme.be.TimeLog;

/**
 *
 * @author WøbbePC
 */
public class Project {
    
    private int id;
    private String name;
    private String client;
    private int cost;
    private List<TimeLog> projectTime;

    public Project(int id, String name, String client, int cost) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.cost = cost;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<TimeLog> getTime() {
        return projectTime;
    }

    public void setTime(List<TimeLog> time) {
        this.projectTime = time;
    }
    
    public void addTime(TimeLog time){
        projectTime.add(time);
    }
    
    
    
}
