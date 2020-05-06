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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackme.be.Task;
import trackme.be.TimeLog;
import trackme.be.TimeLog.TimeType;
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
        String sql = "INSERT INTO [TimeLog] (userId, projectId, taskId, time, typeOfTime)VALUES (?,?,?,CURRENT_TIMESTAMP,?)";

        try ( Connection con = connection.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, projectId);
            pstmt.setInt(3, taskId);

            if (timeLog.getType() == TimeLog.TimeType.PLAY) {
                pstmt.setInt(4, 1);
            } else if (timeLog.getType() == TimeLog.TimeType.STOP) {
                pstmt.setInt(4, 2);
            } else {
                pstmt.setInt(4, 3);
            }

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

                if (projectHash.get(id) == null) {
                    projectHash.put(id, new Project(id, projectName, clientName, cost));
                }
                projectHash.get(id).addTasks(new Task(taskId, taskName, taskDescription));

                switch (typeOfTime) {
                    case 1:
                        projectHash.get(id).addTime(new TimeLog(TimeLog.TimeType.PLAY, time));
                        break;
                    case 2:
                        projectHash.get(id).addTime(new TimeLog(TimeLog.TimeType.STOP, time));
                        break;
                    case 3:
                        projectHash.get(id).addTime(new TimeLog(TimeLog.TimeType.PAUSE, time));
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
}
