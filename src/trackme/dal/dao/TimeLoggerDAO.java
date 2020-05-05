/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal.dao;

import trackme.be.Project;
import trackme.dal.DBConnectionProvider;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public TimeLoggerDAO() {
        connection = new DBConnectionProvider();
    }

    public void insertTimeLog(int userId, int projectId, int taskId, TimeLog timeLog) throws SQLServerException {
        String sql = "INSERT INTO [TimeLog] (userId, projectId, taskId, " + timeType + ")VALUES (?,?,?,CURRENT_TIMESTAMP)";
        if (timeLog.getType() == TimeLog.TimeType.PLAY) {
            timeType = "startTime";
        }
        if (timeLog.getType() == TimeLog.TimeType.STOP) {
            timeType = "endTime";
        }
        if (timeLog.getType() == TimeLog.TimeType.PAUSE) {
            timeType = "pauseTime";
        }
        try ( Connection con = connection.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, projectId);
            pstmt.setInt(3, taskId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TimeLoggerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Project> getUserProjectTime(User user) throws SQLServerException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM TimeLog JOIN Projects ON Projects.id = TimeLog.projectId JOIN Task ON Task.id = TimeLog.taskId WHERE userId = ?";

        try ( Connection con = connection.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("projectId");
                String projectName = rs.getString("name");
                String clientName = rs.getString("clientName");
                int cost = rs.getInt("cost");
                int taskId = rs.getInt("taskId");
                String taskDescription = rs.getString("description");
                String taskName = rs.getString("taskName");
                LocalDateTime start = rs.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime stop = rs.getTimestamp("endTime").toLocalDateTime();
                LocalDateTime pause = rs.getTimestamp("pauseTime").toLocalDateTime();
                projects.add(new Project(id, projectName, clientName, cost));

                for (Project project : projects) {
                    if(project.getId() == id){
                    project.addTasks(new Task(taskId, taskName, taskDescription));
                    break;
                    }
                }
                
                if (start != null) {
                    for (Project project : projects) {
                        if (project.getId() == id) {
                            project.addTime(new TimeLog(TimeLog.TimeType.PLAY, start));
                            break;
                        }
                    }
                }
                if (stop != null) {
                    for (Project project : projects) {
                        if (project.getId() == id) {
                            project.addTime(new TimeLog(TimeLog.TimeType.STOP, stop));
                            break;
                        }
                    }
                }
                if (pause != null) {
                    for (Project project : projects) {
                        if (project.getId() == id) {
                            project.addTime(new TimeLog(TimeLog.TimeType.PAUSE, pause));
                            break;
                        }
                    }
                }
            }
            return projects;
        } catch (SQLException ex) {
            Logger.getLogger(TimeLoggerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Task> getTaskForUser(User user){
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM ";
        
        return null;
    }
}
