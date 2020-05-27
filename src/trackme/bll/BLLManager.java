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
import trackme.dal.DALManager;

/**
 *
 * @author mac
 */
public class BLLManager implements IBLLFacade {
    
    private final Filter filter;
    private DALManager dalManager;
    private final TimeConverter timeConverter;

    public BLLManager() {
        dalManager = new DALManager();
        timeConverter = new TimeConverter();

        filter = new Filter();
//        List<Project> pros = dalManager.getAllProjects();
//        List<Project> pros = dalManager.getUserProjectTime();


    }

    @Override
    public User loginUser(String username, String password) {
        return dalManager.getUser(username, password);
    }

    @Override
    public List<Task> getTasksForProject(Project project) throws SQLServerException{
        return dalManager.getTasksForProject(project);
    }

    @Override
    public List<Project> getUserProjectTime(User user) throws SQLServerException {
        return dalManager.getUserProjectTime(user);
    }

    @Override
    public List<Project> getProjectsForUser(User user) throws SQLException {
        return dalManager.getProjectsForUser(user);
    }

    @Override
    public void insertTaskForProject(Project project, String name, String description, int toPay) throws SQLServerException {
        dalManager.insertTaskForProject(project, name, description, toPay);
    

    
    }

    @Override
    public void insertTimeLog(User user, Project project, Task task, int timeType) throws SQLServerException {
        dalManager.insertTimeLog(user, project, task, timeType);
    }

    @Override
    public void getTimeForTask(User user, Task task) throws SQLServerException {
        dalManager.getTimeForTask(user, task);
    }

    @Override
    public void getTotalTimeForTask(Task task) throws SQLServerException {
        timeConverter.getTotalTimeForTask(task);
    }

    @Override
    public void getTotalTimeForEachProject(List<Project> projects) {
        timeConverter.getTotalTimeForEachProject(projects);
    }

    @Override

    public List<User> getAllUsers() throws SQLServerException {
       return dalManager.getAllUsers();

    }
    
    @Override
    public List<Task> getAllTaskLogsForProject(User user, Project project) throws SQLServerException {
        return dalManager.getAllTaskLogsForProject(user, project);

    }

    @Override
    public void createNewUser(String name, String password, String email, int isAdmin) throws SQLServerException {
         dalManager.createNewUser(name, password, email, isAdmin);
    }

    @Override
    public void addEditUser(User user, String name, String email, String password, int isAdmin) {
        dalManager.addEditUser(user, name, email, password, isAdmin);
    }

    @Override
    public void deleteUser(User user) throws SQLServerException {
        dalManager.deleteUser(user);
    }

    @Override

    public List<Task> filterList(LocalDate fromTime, LocalDate toTime, List<Task> tasks) throws ParseException {
        return filter.filterList(fromTime, toTime, tasks);
    }

    @Override
    public List<LocalDateTime> calculateTime(LocalTime startTime, LocalTime endTime, LocalDate date) {
        return timeConverter.calculateTime(startTime, endTime, date);
    }

    @Override
    public void editTimeLog(User user, Project project, Task task, LocalDateTime startTime, LocalDateTime endTime) throws SQLServerException {
        dalManager.editTimeLog(user, project, task, startTime, endTime);
    }

    @Override
    public List<Project> getAllProjects() throws SQLServerException {
        return   dalManager.getAllProjects();
    }

    @Override
    public void createProject(String name, String clientName, String cost) throws SQLServerException {
        dalManager.createProject(name, clientName, cost);
    }

    @Override
    public void getAllTimeLogsForTask(Task task) throws SQLServerException {
        dalManager.getAllTimeLogsForTask(task);
    }

    @Override
    public String convertSecondsToHourMinuteSecond(Task task) {
        return timeConverter.convertSecondsToHourMinuteSecond(task);
    }
}