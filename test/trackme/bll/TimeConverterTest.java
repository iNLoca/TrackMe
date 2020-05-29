/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.Test;
import trackme.be.Project;
import trackme.be.Task;

/**
 *
 * @author mega_
 */
public class TimeConverterTest {
    
    public TimeConverterTest() {
    }
    

    /**
     * Test of getProjectTime method, of class TimeConverter.
     */
    @Test
    public void testGetProjectTime() {
        System.out.println("getProjectTime");
        List<Project> projects = null;
        TimeConverter instance = new TimeConverter();
        instance.getProjectTime(projects);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalTimeForTask method, of class TimeConverter.
     */
    @Test
    public void testGetTotalTimeForTask() throws Exception {
        System.out.println("getTotalTimeForTask");
        Task task = null;
        TimeConverter instance = new TimeConverter();
        instance.getTotalTimeForTask(task);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalTimeForEachProject method, of class TimeConverter.
     */
    @Test
    public void testGetTotalTimeForEachProject() {
        System.out.println("getTotalTimeForEachProject");
        List<Project> projects = null;
        TimeConverter instance = new TimeConverter();
        instance.getTotalTimeForEachProject(projects);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateTime method, of class TimeConverter.
     */
    @Test
    public void testCalculateTime() {
        System.out.println("calculateTime");
        LocalTime startTime = null;
        LocalTime endTime = null;
        LocalDate date = null;
        TimeConverter instance = new TimeConverter();
        List<LocalDateTime> expResult = null;
        List<LocalDateTime> result = instance.calculateTime(startTime, endTime, date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
