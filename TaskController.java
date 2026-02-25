package com.resume.taskapi.controller;

import com.resume.taskapi.model.Task;
import com.resume.taskapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Task Management API.
 * Demonstrates RESTful API design principles.
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * POST /api/tasks - Create a new task
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * GET /api/tasks - Get all tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/{id} - Get task by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/tasks/{id} - Update a task
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable String id,
            @RequestBody Task task) {
        return taskService.updateTask(id, task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/tasks/{id} - Delete a task
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.notFound().build();
    }

    /**
     * GET /api/tasks/priority - Get tasks sorted by priority
     */
    @GetMapping("/priority")
    public ResponseEntity<List<Task>> getTasksByPriority() {
        List<Task> tasks = taskService.getTasksByPriority();
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/next - Get next highest priority task
     */
    @GetMapping("/next")
    public ResponseEntity<Task> getNextTask() {
        return taskService.getNextTask()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    /**
     * GET /api/tasks/top/{k} - Get top K priority tasks
     */
    @GetMapping("/top/{k}")
    public ResponseEntity<List<Task>> getTopPriorityTasks(@PathVariable int k) {
        List<Task> tasks = taskService.getTopPriorityTasks(k);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/search?keyword={keyword} - Search tasks by keyword
     */
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(
            @RequestParam(required = false) String keyword) {
        List<Task> tasks = taskService.searchTasks(keyword);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/filter - Filter tasks by criteria
     * Query params: status, priority, assignedTo
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(
            @RequestParam(required = false) Task.Status status,
            @RequestParam(required = false) Task.Priority priority,
            @RequestParam(required = false) String assignedTo) {
        List<Task> tasks = taskService.filterTasks(status, priority, assignedTo);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/grouped - Get tasks grouped by status
     */
    @GetMapping("/grouped")
    public ResponseEntity<Map<Task.Status, List<Task>>> getTasksByStatus() {
        Map<Task.Status, List<Task>> grouped = taskService.getTasksByStatus();
        return ResponseEntity.ok(grouped);
    }

    /**
     * GET /api/tasks/overdue - Get overdue tasks
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> tasks = taskService.getOverdueTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/statistics - Get task statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = taskService.getStatistics();
        return ResponseEntity.ok(stats);
    }
}
