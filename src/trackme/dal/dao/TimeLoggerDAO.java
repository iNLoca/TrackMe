/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal.dao;

import trackme.be.Project;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.Task;
import trackme.be.TimeLog;
import trackme.be.User;
import trackme.dal.DBConnectionProvider;

/**
 *
 * @author WøbbePC
 */
public class TimeLoggerDAO {

    private final DBConnectionProvider connection;
    private String timeType;
    private Task tempTask = null;

    public TimeLoggerDAO() {
        connection = new DBConnectionProvider();
    }

    public void insertTimeLog(User user, Project project, Task task, int timeType) throws SQLServerException {
        String sql = "INSERT INTO [TimeLog] (userId, projectId, taskId, time, typeOfTime)VALUES (?,?,?,CURRENT_TIMESTAMP,?)";

        try ( Connection con = connection.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.setInt(2, project.getId());
            pstmt.setInt(3, task.getId());
            pstmt.setInt(4, timeType);

//            if (timeLog.getType() == TimeLog.TimeType.PLAY) {
//                pstmt.setInt(4, 1);
//            } else if (timeLog.getType() == TimeLog.TimeType.STOP) {
//                pstmt.setInt(4, 2);
//            } else {
//                pstmt.setInt(4, 3);
//            }

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TimeLoggerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NullPointerException ea){
            System.out.println("cant be null");
            
        }
    }
    
    public void editTimeLog(User user, Project project, Task task, LocalDateTime startTime, LocalDateTime endTime) throws SQLServerException{
    String sql = "INSERT INTO [TimeLog] (userId, projectId, taskId, time, typeOfTime) VALUES (?,?,?,?,?),(?,?,?,?,?)";
    
    try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, user.getId());
        pstmt.setInt(2, project.getId());
        pstmt.setInt(3, task.getId());
        pstmt.setTimestamp(4, Timestamp.valueOf(startTime));
        pstmt.setInt(5, 1);
        
        pstmt.setInt(6, user.getId());
        pstmt.setInt(7, project.getId());
        pstmt.setInt(8, task.getId());
        pstmt.setTimestamp(9, Timestamp.valueOf(endTime));
        pstmt.setInt(10, 2);
        pstmt.executeUpdate();
        
    }   catch (SQLException ex) {
            Logger.getLogger(TimeLoggerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public void getTimeForTask(User user, Task task) throws SQLServerException{
    String sql = "SELECT * FROM TimeLog WHERE userId = ? AND taskId = ? ";
    
    try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, user.getId());
        pstmt.setInt(2, task.getId());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
        LocalDateTime time = rs.getTimestamp("time").toLocalDateTime();
        int typeOfTime = rs.getInt("typeOfTime");
        switch (typeOfTime) {
                    case 1:
                        task.addTime(new TimeLog(TimeLog.TimeType.PLAY, time));
                        break;
                    case 2:
                        task.addTime(new TimeLog(TimeLog.TimeType.STOP, time));
                        break;
                }
        }
    }   catch (SQLException ex) {
            Logger.getLogger(TimeLoggerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public List<Task> getAllTaskLogsForProject(User user, Project project) throws SQLServerException{
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM TimeLog JOIN Task on TimeLog.taskId = Task.id WHERE projectId = ? AND userId = ? ORDER BY taskId, userId";
        int i =0;
        
        try(Connection con = connection.getConnection()){
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, project.getId());
            pstmt.setInt(2, user.getId());
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                int taskId = rs.getInt("taskId");
                String taskDescription = rs.getString("description");
                String taskName = rs.getString("taskName");
                int toPay = rs.getInt("toPay");
                LocalDateTime time = rs.getTimestamp("time").toLocalDateTime();
                if(i==0){
                tempTask = new Task(taskId, taskName, taskDescription, toPay);
                tempTask.addTime(new TimeLog(TimeLog.TimeType.PLAY, time));
                i++;
                }
                else if (i==1){
                    tempTask.addTime(new TimeLog(TimeLog.TimeType.STOP, time));
                    tasks.add(tempTask);
                    i=0;
                    tempTask=null;
                }
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TimeLoggerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tasks;
    }
    
    public List<Project> getUserProjectTime(User user) throws SQLServerException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM TimeLog JOIN Projects ON Projects.id = TimeLog.projectId JOIN Task ON Task.id = TimeLog.taskId WHERE userId = ?";

        try ( Connection con = connection.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            HashMap<Integer, Project> projectHash = new HashMap<Integer, Project>();
            while (rs.next()) {
                int id = rs.getInt("projectId");
                String projectName = rs.getString("name");
                String clientName = rs.getString("clientName");
                int cost = rs.getInt("cost");
                int taskId = rs.getInt("taskId");
                String taskDescription = rs.getString("description");
                String taskName = rs.getString("taskName");
                LocalDateTime time = rs.getTimestamp("time").toLocalDateTime();
                int typeOfTime = rs.getInt("typeOfTime");
                int toPay = rs.getInt("toPay");

                if (projectHash.get(id) == null) {
                    projectHash.put(id, new Project(id, projectName, clientName, cost));
                }
                projectHash.get(id).addTasks(new Task(taskId, taskName, taskDescription, toPay));

                switch (typeOfTime) {
                    case 1:
                        projectHash.get(id).addTime(new TimeLog(TimeLog.TimeType.PLAY, time));
                        break;
                    case 2:
                        projectHash.get(id).addTime(new TimeLog(TimeLog.TimeType.STOP, time));
                        break;
                }

            }
            for (Project p : projectHash.values()) {            
                projects.add(p);
            }
            return projects;
        } catch (SQLException ex) {
            Logger.getLogger(TimeLoggerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void getAllTimeLogsForTask(Task task) throws SQLServerException{
    String sql = "SELECT * FROM TimeLog WHERE taskId = ? ORDER BY taskId, userId";
    try(Connection con = connection.getConnection()){
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, task.getId());
        ResultSet rs = pstmt.executeQuery();
                        
        while(rs.next()){
            int typeOfTime = rs.getInt("typeOfTime");
            LocalDateTime time = rs.getTimestamp("time").toLocalDateTime();
            switch (typeOfTime) {
                    case 1:
                        task.addTime(new TimeLog(TimeLog.TimeType.PLAY, time));
                        break;
                    case 2:
                        task.addTime(new TimeLog(TimeLog.TimeType.STOP, time));
                        break;
                }
        }
        
    }   catch (SQLException ex) {
            Logger.getLogger(TimeLoggerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
}
