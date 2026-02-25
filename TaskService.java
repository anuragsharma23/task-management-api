package com.resume.taskapi.service;

import com.resume.taskapi.algorithm.TaskSearchAlgorithm;
import com.resume.taskapi.datastructures.TaskPriorityQueue;
import com.resume.taskapi.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service layer handling business logic for task management.
 * Demonstrates separation of concerns and service design patterns.
 */
@Service
public class TaskService {
    
    private final Map<String, Task> taskRepository;
    private final TaskPriorityQueue priorityQueue;
    private int taskIdCounter;

    public TaskService() {
        this.taskRepository = new ConcurrentHashMap<>();
        this.priorityQueue = new TaskPriorityQueue();
        this.taskIdCounter = 1;
    }

    /**
     * Creates a new task.
     */
    public Task createTask(Task task) {
        String id = generateTaskId();
        task.setId(id);
        task.setCreatedAt(java.time.LocalDateTime.now());
        
        if (task.getStatus() == null) {
            task.setStatus(Task.Status.TODO);
        }
        
        taskRepository.put(id, task);
        priorityQueue.insert(task);
        
        return task;
    }

    /**
     * Retrieves a task by ID.
     */
    public Optional<Task> getTaskById(String id) {
        return Optional.ofNullable(taskRepository.get(id));
    }

    /**
     * Retrieves all tasks.
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskRepository.values());
    }

    /**
     * Gets tasks sorted by priority.
     */
    public List<Task> getTasksByPriority() {
        return priorityQueue.getSortedTasks();
    }

    /**
     * Gets the next highest priority task.
     */
    public Optional<Task> getNextTask() {
        return Optional.ofNullable(priorityQueue.peek());
    }

    /**
     * Updates an existing task.
     */
    public Optional<Task> updateTask(String id, Task updatedTask) {
        Task existingTask = taskRepository.get(id);
        
        if (existingTask == null) {
            return Optional.empty();
        }

        // Track if priority changed
        Task.Priority oldPriority = existingTask.getPriority();
        
        // Update fields
        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getPriority() != null) {
            existingTask.setPriority(updatedTask.getPriority());
        }
        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }
        if (updatedTask.getDueDate() != null) {
            existingTask.setDueDate(updatedTask.getDueDate());
        }
        if (updatedTask.getAssignedTo() != null) {
            existingTask.setAssignedTo(updatedTask.getAssignedTo());
        }

        // Update priority queue if priority changed
        if (!oldPriority.equals(existingTask.getPriority())) {
            priorityQueue.updatePriority(id, existingTask.getPriority());
        }

        return Optional.of(existingTask);
    }

    /**
     * Deletes a task by ID.
     */
    public boolean deleteTask(String id) {
        Task removed = taskRepository.remove(id);
        if (removed != null) {
            priorityQueue.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Searches tasks by keyword.
     */
    public List<Task> searchTasks(String keyword) {
        List<Task> allTasks = getAllTasks();
        return TaskSearchAlgorithm.searchByKeyword(allTasks, keyword);
    }

    /**
     * Filters tasks by multiple criteria.
     */
    public List<Task> filterTasks(Task.Status status, Task.Priority priority, String assignedTo) {
        List<Task> allTasks = getAllTasks();
        return TaskSearchAlgorithm.multiCriteriaFilter(allTasks, status, priority, assignedTo);
    }

    /**
     * Gets top K priority tasks.
     */
    public List<Task> getTopPriorityTasks(int k) {
        List<Task> allTasks = getAllTasks();
        return TaskSearchAlgorithm.findTopKPriorityTasks(allTasks, k);
    }

    /**
     * Gets tasks grouped by status.
     */
    public Map<Task.Status, List<Task>> getTasksByStatus() {
        List<Task> allTasks = getAllTasks();
        return TaskSearchAlgorithm.groupByStatus(allTasks);
    }

    /**
     * Gets overdue tasks.
     */
    public List<Task> getOverdueTasks() {
        List<Task> allTasks = getAllTasks();
        return TaskSearchAlgorithm.findOverdueTasks(allTasks);
    }

    /**
     * Gets task statistics.
     */
    public Map<String, Object> getStatistics() {
        List<Task> allTasks = getAllTasks();
        return TaskSearchAlgorithm.calculateStatistics(allTasks);
    }

    /**
     * Generates a unique task ID.
     */
    private synchronized String generateTaskId() {
        return "TASK-" + String.format("%04d", taskIdCounter++);
    }
}
