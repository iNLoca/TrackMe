/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.dal;
import java.time.LocalDateTime;
import java.util.List;
import trackme.be.Project;
import trackme.be.Task;
import trackme.be.TimeLog;
import trackme.be.User;
import trackme.dal.dao.TaskDAO;
import trackme.dal.dao.UserDAO;
import trackme.dal.dao.TimeLoggerDAO;
import trackme.dal.dao.ProjectDAO;

/**
 *
 * @author WøbbePC
 */
public class DALManager implements IDALFacade {

    private final UserDAO userDAO;
    private final TimeLoggerDAO timeLoggerDAO;
    private final TaskDAO taskDAO;
    private final ProjectDAO projectDAO;

    public DALManager() {
        this.userDAO = new UserDAO();
        this.timeLoggerDAO = new TimeLoggerDAO();
        this.taskDAO = new TaskDAO();
        this.projectDAO = new ProjectDAO();
    }

    @Override
    public User getUser(String email, String password) {
        return userDAO.getUser(email, password);

    }

    @Override
    public List<Project> getUserProjectTime(User user) {
        return timeLoggerDAO.getUserProjectTime(user);
    }


    public List<Task> getTasksForProject(Project project) {
        return taskDAO.getTasksForProject(project);
    }

    @Override
    public void insertTaskForProject(Project project, String name, String description, int toPay) {
        taskDAO.insertTask(project, name, description, toPay);
    }

    @Override
    public void insertTimeLog(User user, Project project, Task task, int timeType) {
        timeLoggerDAO.insertTimeLog(user, project, task, timeType);
    }

    @Override
    public void getTimeForTask(User user, Task task) {
        timeLoggerDAO.getTimeForTask(user, task);
    }

    @Override

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public List<Task> getAllTaskLogsForProject(User user, Project project) {
        return timeLoggerDAO.getAllTaskLogsForProject(user, project);

    }

    @Override
    public void createNewUser(String name, String password, String email, int isAdmin) {
        userDAO.createNewUser(name, password, email, isAdmin);
    }

    @Override
    public void addEditUser(User user, String name, String email, String password, int isAdmin) {
        userDAO.addEditUser(user, name, email, password, isAdmin);
    }

    @Override
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

    @Override
    public void editTimeLog(User user, Project project, Task task, LocalDateTime startTime, LocalDateTime endTime) {
        timeLoggerDAO.editTimeLog(user, project, task, startTime, endTime);
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projectList = projectDAO.getAllProjects();
        List<TimeLog> timeLogList = timeLoggerDAO.getAllTimeLogsForTask();
        
        for (Project project : projectList) {
            for (Task task : project.getTasks()) {
                for (TimeLog timeLog : timeLogList) {
                    if(task.getId() == timeLog.getTaskID()){
                        task.addTime(timeLog);
                    }
                }
            }
        }
        return projectList;
    }

    @Override
    public void createProject(String name, String clientName, String cost) {
        projectDAO.createProject(name, clientName, cost);
    }

    @Override
    public void getAllTimeLogsForTask(Task task) {
        timeLoggerDAO.getAllTimeLogsForTask();

    }

}
