package com.resume.taskapi.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Task model representing a task in the system.
 * Implements Comparable for priority-based sorting.
 */
public class Task implements Comparable<Task> {
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private String assignedTo;

    public enum Priority {
        LOW(1), MEDIUM(2), HIGH(3), CRITICAL(4);
        
        private final int value;
        
        Priority(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    public enum Status {
        TODO, IN_PROGRESS, COMPLETED, CANCELLED
    }

    public Task() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.TODO;
    }

    public Task(String id, String title, String description, Priority priority) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public int compareTo(Task other) {
        // Compare by priority (higher priority first), then by due date
        int priorityComparison = Integer.compare(
            other.priority.getValue(), 
            this.priority.getValue()
        );
        
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        
        if (this.dueDate != null && other.dueDate != null) {
            return this.dueDate.compareTo(other.dueDate);
        }
        
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", dueDate=" + dueDate +
                '}';
    }
}
