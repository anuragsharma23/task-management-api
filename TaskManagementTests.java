package com.resume.taskapi;

import com.resume.taskapi.datastructures.TaskPriorityQueue;
import com.resume.taskapi.model.Task;
import com.resume.taskapi.algorithm.TaskSearchAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests demonstrating testing best practices.
 */
class TaskManagementTests {

    private TaskPriorityQueue priorityQueue;
    private List<Task> testTasks;

    @BeforeEach
    void setUp() {
        priorityQueue = new TaskPriorityQueue();
        testTasks = createTestTasks();
    }

    @Test
    void testPriorityQueueInsertAndPoll() {
        Task highPriority = new Task("1", "Critical Bug", "Fix production issue", 
                                     Task.Priority.CRITICAL);
        Task lowPriority = new Task("2", "Documentation", "Update README", 
                                    Task.Priority.LOW);

        priorityQueue.insert(lowPriority);
        priorityQueue.insert(highPriority);

        Task polled = priorityQueue.poll();
        assertEquals("Critical Bug", polled.getTitle());
        assertEquals(Task.Priority.CRITICAL, polled.getPriority());
    }

    @Test
    void testPriorityQueueRemove() {
        Task task1 = new Task("1", "Task 1", "Description", Task.Priority.HIGH);
        Task task2 = new Task("2", "Task 2", "Description", Task.Priority.MEDIUM);

        priorityQueue.insert(task1);
        priorityQueue.insert(task2);

        assertTrue(priorityQueue.remove("2"));
        assertEquals(1, priorityQueue.size());
    }

    @Test
    void testPriorityUpdate() {
        Task task = new Task("1", "Task", "Description", Task.Priority.LOW);
        priorityQueue.insert(task);

        assertTrue(priorityQueue.updatePriority("1", Task.Priority.CRITICAL));
        
        Task polled = priorityQueue.peek();
        assertEquals(Task.Priority.CRITICAL, polled.getPriority());
    }

    @Test
    void testSearchByKeyword() {
        List<Task> results = TaskSearchAlgorithm.searchByKeyword(testTasks, "bug");
        
        assertFalse(results.isEmpty());
        assertTrue(results.stream()
                .anyMatch(t -> t.getTitle().toLowerCase().contains("bug")));
    }

    @Test
    void testMultiCriteriaFilter() {
        List<Task> results = TaskSearchAlgorithm.multiCriteriaFilter(
            testTasks, 
            Task.Status.TODO, 
            Task.Priority.HIGH, 
            null
        );

        results.forEach(task -> {
            assertEquals(Task.Status.TODO, task.getStatus());
            assertEquals(Task.Priority.HIGH, task.getPriority());
        });
    }

    @Test
    void testTopKPriorityTasks() {
        List<Task> top3 = TaskSearchAlgorithm.findTopKPriorityTasks(testTasks, 3);
        
        assertEquals(3, top3.size());
        
        // Verify they're in priority order
        for (int i = 0; i < top3.size() - 1; i++) {
            assertTrue(top3.get(i).getPriority().getValue() >= 
                      top3.get(i + 1).getPriority().getValue());
        }
    }

    @Test
    void testGroupByStatus() {
        Map<Task.Status, List<Task>> grouped = 
            TaskSearchAlgorithm.groupByStatus(testTasks);

        assertTrue(grouped.containsKey(Task.Status.TODO));
        assertTrue(grouped.containsKey(Task.Status.IN_PROGRESS));
    }

    @Test
    void testCalculateStatistics() {
        Map<String, Object> stats = TaskSearchAlgorithm.calculateStatistics(testTasks);

        assertNotNull(stats.get("totalTasks"));
        assertNotNull(stats.get("statusCounts"));
        assertNotNull(stats.get("priorityCounts"));
        assertNotNull(stats.get("completionRate"));
    }

    @Test
    void testTaskComparison() {
        Task high = new Task("1", "High", "Desc", Task.Priority.HIGH);
        Task low = new Task("2", "Low", "Desc", Task.Priority.LOW);

        assertTrue(high.compareTo(low) < 0); // High priority comes first
    }

    private List<Task> createTestTasks() {
        List<Task> tasks = new ArrayList<>();

        Task task1 = new Task("1", "Fix critical bug", "Production issue", 
                             Task.Priority.CRITICAL);
        task1.setStatus(Task.Status.IN_PROGRESS);
        task1.setAssignedTo("john@example.com");

        Task task2 = new Task("2", "Update documentation", "README updates", 
                             Task.Priority.LOW);
        task2.setStatus(Task.Status.TODO);

        Task task3 = new Task("3", "Implement feature", "New feature request", 
                             Task.Priority.HIGH);
        task3.setStatus(Task.Status.TODO);
        task3.setDueDate(LocalDateTime.now().plusDays(7));

        Task task4 = new Task("4", "Code review", "Review PR #123", 
                             Task.Priority.MEDIUM);
        task4.setStatus(Task.Status.COMPLETED);

        Task task5 = new Task("5", "Fix bug in login", "User reported issue", 
                             Task.Priority.HIGH);
        task5.setStatus(Task.Status.TODO);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);

        return tasks;
    }
}
