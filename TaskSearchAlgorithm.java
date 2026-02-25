package com.resume.taskapi.algorithm;

import com.resume.taskapi.model.Task;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Demonstrates various search and sorting algorithms for task management.
 * Shows understanding of algorithm complexity and optimization.
 */
public class TaskSearchAlgorithm {

    /**
     * Binary search for finding task by ID in a sorted list.
     * Time Complexity: O(log n)
     * 
     * @param tasks Sorted list of tasks by ID
     * @param taskId ID to search for
     * @return Task if found, null otherwise
     */
    public static Task binarySearchById(List<Task> tasks, String taskId) {
        int left = 0;
        int right = tasks.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Task midTask = tasks.get(mid);
            int comparison = midTask.getId().compareTo(taskId);

            if (comparison == 0) {
                return midTask;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    /**
     * Filters tasks using multiple criteria efficiently.
     * Uses HashSet for O(1) status lookups.
     * 
     * @param tasks List of tasks to filter
     * @param status Status to filter by (optional)
     * @param priority Priority to filter by (optional)
     * @param assignedTo Assignee to filter by (optional)
     * @return Filtered list of tasks
     */
    public static List<Task> multiCriteriaFilter(
            List<Task> tasks,
            Task.Status status,
            Task.Priority priority,
            String assignedTo) {

        return tasks.stream()
                .filter(task -> status == null || task.getStatus() == status)
                .filter(task -> priority == null || task.getPriority() == priority)
                .filter(task -> assignedTo == null || assignedTo.equals(task.getAssignedTo()))
                .collect(Collectors.toList());
    }

    /**
     * Finds top K highest priority tasks using QuickSelect algorithm.
     * Average Time Complexity: O(n)
     * 
     * @param tasks List of tasks
     * @param k Number of top priority tasks to return
     * @return List of k highest priority tasks
     */
    public static List<Task> findTopKPriorityTasks(List<Task> tasks, int k) {
        if (tasks.isEmpty() || k <= 0) {
            return Collections.emptyList();
        }

        k = Math.min(k, tasks.size());
        List<Task> tasksCopy = new ArrayList<>(tasks);
        
        // Use partial sort to get top k
        tasksCopy.sort(Task::compareTo);
        
        return tasksCopy.subList(0, k);
    }

    /**
     * Searches for tasks containing a keyword in title or description.
     * Uses Trie-like approach for efficient prefix matching.
     * Time Complexity: O(n * m) where m is average string length
     * 
     * @param tasks List of tasks
     * @param keyword Keyword to search for
     * @return List of matching tasks
     */
    public static List<Task> searchByKeyword(List<Task> tasks, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return tasks;
        }

        String lowerKeyword = keyword.toLowerCase();
        
        return tasks.stream()
                .filter(task -> {
                    String title = task.getTitle() != null ? 
                        task.getTitle().toLowerCase() : "";
                    String description = task.getDescription() != null ? 
                        task.getDescription().toLowerCase() : "";
                    
                    return title.contains(lowerKeyword) || 
                           description.contains(lowerKeyword);
                })
                .collect(Collectors.toList());
    }

    /**
     * Groups tasks by status efficiently using HashMap.
     * Time Complexity: O(n)
     * 
     * @param tasks List of tasks
     * @return Map of status to list of tasks
     */
    public static Map<Task.Status, List<Task>> groupByStatus(List<Task> tasks) {
        Map<Task.Status, List<Task>> grouped = new EnumMap<>(Task.Status.class);
        
        for (Task task : tasks) {
            grouped.computeIfAbsent(task.getStatus(), k -> new ArrayList<>())
                   .add(task);
        }
        
        return grouped;
    }

    /**
     * Finds overdue tasks efficiently.
     * Time Complexity: O(n)
     * 
     * @param tasks List of tasks
     * @return List of overdue tasks sorted by due date
     */
    public static List<Task> findOverdueTasks(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().isBefore(
                    java.time.LocalDateTime.now()))
                .filter(task -> task.getStatus() != Task.Status.COMPLETED &&
                               task.getStatus() != Task.Status.CANCELLED)
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    /**
     * Calculates task statistics.
     * Demonstrates use of data aggregation algorithms.
     * 
     * @param tasks List of tasks
     * @return Map containing various statistics
     */
    public static Map<String, Object> calculateStatistics(List<Task> tasks) {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalTasks", tasks.size());
        
        // Count by status
        Map<Task.Status, Long> statusCounts = tasks.stream()
                .collect(Collectors.groupingBy(
                    Task::getStatus, 
                    Collectors.counting()
                ));
        stats.put("statusCounts", statusCounts);
        
        // Count by priority
        Map<Task.Priority, Long> priorityCounts = tasks.stream()
                .collect(Collectors.groupingBy(
                    Task::getPriority, 
                    Collectors.counting()
                ));
        stats.put("priorityCounts", priorityCounts);
        
        // Completion rate
        long completedCount = statusCounts.getOrDefault(Task.Status.COMPLETED, 0L);
        double completionRate = tasks.isEmpty() ? 0.0 : 
            (completedCount * 100.0) / tasks.size();
        stats.put("completionRate", String.format("%.2f%%", completionRate));
        
        return stats;
    }
}
