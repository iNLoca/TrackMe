/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackme.bll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import trackme.be.Task;
import trackme.be.TimeLog;

/**
 *
 * @author mega_
 */
public class FilterTest {

    public FilterTest() {
    }

    @Test
    public void testFilterListTwo() throws Exception {
        Filter f = new Filter();
        Task t = new Task(0, "DUMMY", "DUMMY", 0);
        t.addTime(new TimeLog(TimeLog.TimeType.PLAY, LocalDateTime.of(2020, Month.MARCH, 10, 0, 0)));
        t.addTime(new TimeLog(TimeLog.TimeType.STOP, LocalDateTime.of(2020, Month.MARCH, 10, 3, 0)));

        Task t2 = new Task(2, "DUMMY2", "DUMMY2", 0);
        t2.addTime(new TimeLog(TimeLog.TimeType.PLAY, LocalDateTime.of(2020, Month.APRIL, 10, 13, 0)));
        t2.addTime(new TimeLog(TimeLog.TimeType.STOP, LocalDateTime.of(2020, Month.APRIL, 10, 16, 15)));

        List<Task> tasks = new ArrayList();
        tasks.add(t);
        tasks.add(t2);

        List<Task> tasksFiltered = f.filterList(LocalDate.of(2020, Month.MARCH, 10), LocalDate.of(2020, Month.APRIL, 11), tasks);
        assertEquals(tasksFiltered.get(0).getId(), 0);
        assertEquals(tasksFiltered.size(), 2);
    }

    @Test
    public void testFilterListSingle() throws Exception {
        Filter f = new Filter();
        Task t = new Task(0, "DUMMY", "DUMMY", 0);
        t.addTime(new TimeLog(TimeLog.TimeType.PLAY, LocalDateTime.of(2020, Month.MARCH, 10, 0, 0)));
        t.addTime(new TimeLog(TimeLog.TimeType.STOP, LocalDateTime.of(2020, Month.MARCH, 10, 3, 0)));

        Task t2 = new Task(2, "DUMMY2", "DUMMY2", 0);
        t2.addTime(new TimeLog(TimeLog.TimeType.PLAY, LocalDateTime.of(2020, Month.APRIL, 10, 13, 0)));
        t2.addTime(new TimeLog(TimeLog.TimeType.STOP, LocalDateTime.of(2020, Month.APRIL, 10, 16, 15)));

        List<Task> tasks = new ArrayList();
        tasks.add(t);
        tasks.add(t2);

        List<Task> tasksFiltered = f.filterList(LocalDate.of(2020, Month.MARCH, 10), LocalDate.of(2020, Month.APRIL, 9), tasks);
        assertEquals(tasksFiltered.get(0).getId(), 0);
        assertEquals(tasksFiltered.size(), 1);
    }

    @Test
    public void testFilterListNone() throws Exception {
        Filter f = new Filter();
        Task t = new Task(0, "DUMMY", "DUMMY", 0);
        t.addTime(new TimeLog(TimeLog.TimeType.PLAY, LocalDateTime.of(2020, Month.MARCH, 10, 0, 0)));
        t.addTime(new TimeLog(TimeLog.TimeType.STOP, LocalDateTime.of(2020, Month.MARCH, 10, 3, 0)));

        Task t2 = new Task(2, "DUMMY2", "DUMMY2", 0);
        t2.addTime(new TimeLog(TimeLog.TimeType.PLAY, LocalDateTime.of(2020, Month.APRIL, 10, 13, 0)));
        t2.addTime(new TimeLog(TimeLog.TimeType.STOP, LocalDateTime.of(2020, Month.APRIL, 10, 16, 15)));

        List<Task> tasks = new ArrayList();
        tasks.add(t);
        tasks.add(t2);

        List<Task> tasksFiltered = f.filterList(LocalDate.of(2019, Month.MARCH, 10), LocalDate.of(2019, Month.APRIL, 11), tasks);
        assertEquals(tasksFiltered.size(), 0);

    }

    @Test
    public void testFilterListEmpty() throws Exception {
        Filter f = new Filter();
        List<Task> tasks = new ArrayList();

        List<Task> tasksFiltered = f.filterList(LocalDate.of(2019, Month.MARCH, 10), LocalDate.of(2019, Month.APRIL, 11), tasks);
        assertEquals(tasksFiltered.size(), 0);
    }
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testFilterListNull() throws Exception {
        Filter f = new Filter();
        List<Task> tasks = null;
        exceptionRule.expect(NullPointerException.class);
        List<Task> tasksFiltered = f.filterList(LocalDate.of(2019, Month.MARCH, 10), LocalDate.of(2019, Month.APRIL, 11), tasks);
    }

}
