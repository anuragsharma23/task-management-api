package com.resume.taskapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class.
 * Entry point for the Task Management API.
 */
@SpringBootApplication
public class TaskManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApiApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("Task Management API is running!");
        System.out.println("API Base URL: http://localhost:8080/api/tasks");
        System.out.println("===========================================\n");
    }
}
