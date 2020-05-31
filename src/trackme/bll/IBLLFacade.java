/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.User;

/**
 *
 * @author WøbbePC
 */
public interface IBLLFacade {

    public User loginUser(String username, String password);
    
    public List<User> getAllUsers();
    public List<Task> getTasksForProject(Project project);
    public List<Project> getUserProjectTime(User user);
    public void insertTaskForProject(Project project, String name, String description, int toPay);
    public void insertTimeLog(User user, Project project, Task task, int timeType);
    public void getTimeForTask(User user, Task task);
    public void getTotalTimeForTask(Task task);
    public void  getTotalTimeForEachProject(List<Project> projects);
    public List<Task> getAllTaskLogsForProject(User user, Project project);
    public void createNewUser(String name, String password, String email, int isAdmin);
    public void addEditUser(User user, String name, String email, String password, int isAdmin);
    public void deleteUser(User user);
    public List<Task> filterList(LocalDate fromTime, LocalDate toTime, List<Task> tasks);
    public List<LocalDateTime> calculateTime(LocalTime startTime, LocalTime endTime, LocalDate date);
    public void editTimeLog(User user, Project project, Task task, LocalDateTime startTime, LocalDateTime endTime) ;
    public List<Project> getAllProjects();
    public void createProject(String name, String clientName, String cost);
    public void getAllTimeLogsForTask(Task task);
    public String convertSecondsToHourMinuteSecond(Task task);

}
