package com.resume.taskapi.datastructures;

import com.resume.taskapi.model.Task;
import java.util.*;

/**
 * Custom Min-Heap based Priority Queue implementation for Task management.
 * Demonstrates understanding of heap data structure and its operations.
 * 
 * Time Complexity:
 * - Insert: O(log n)
 * - Remove highest priority: O(log n)
 * - Peek: O(1)
 * - Search by ID: O(n)
 */
public class TaskPriorityQueue {
    private final List<Task> heap;
    private final Map<String, Integer> idToIndexMap;

    public TaskPriorityQueue() {
        this.heap = new ArrayList<>();
        this.idToIndexMap = new HashMap<>();
    }

    /**
     * Inserts a task into the priority queue.
     * Maintains heap property by bubbling up.
     */
    public void insert(Task task) {
        heap.add(task);
        int index = heap.size() - 1;
        idToIndexMap.put(task.getId(), index);
        bubbleUp(index);
    }

    /**
     * Removes and returns the highest priority task.
     */
    public Task poll() {
        if (heap.isEmpty()) {
            return null;
        }

        Task highestPriority = heap.get(0);
        Task lastTask = heap.remove(heap.size() - 1);
        idToIndexMap.remove(highestPriority.getId());

        if (!heap.isEmpty()) {
            heap.set(0, lastTask);
            idToIndexMap.put(lastTask.getId(), 0);
            bubbleDown(0);
        }

        return highestPriority;
    }

    /**
     * Returns the highest priority task without removing it.
     */
    public Task peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    /**
     * Removes a specific task by ID.
     */
    public boolean remove(String taskId) {
        Integer index = idToIndexMap.get(taskId);
        if (index == null) {
            return false;
        }

        Task removedTask = heap.get(index);
        Task lastTask = heap.remove(heap.size() - 1);
        idToIndexMap.remove(taskId);

        if (index < heap.size()) {
            heap.set(index, lastTask);
            idToIndexMap.put(lastTask.getId(), index);
            
            // Maintain heap property
            bubbleDown(index);
            bubbleUp(index);
        }

        return true;
    }

    /**
     * Updates a task's priority and maintains heap property.
     */
    public boolean updatePriority(String taskId, Task.Priority newPriority) {
        Integer index = idToIndexMap.get(taskId);
        if (index == null) {
            return false;
        }

        Task task = heap.get(index);
        Task.Priority oldPriority = task.getPriority();
        task.setPriority(newPriority);

        // Maintain heap property based on priority change
        if (newPriority.getValue() > oldPriority.getValue()) {
            bubbleUp(index);
        } else if (newPriority.getValue() < oldPriority.getValue()) {
            bubbleDown(index);
        }

        return true;
    }

    /**
     * Returns all tasks as a list (unordered).
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(heap);
    }

    /**
     * Returns all tasks sorted by priority.
     */
    public List<Task> getSortedTasks() {
        List<Task> sorted = new ArrayList<>(heap);
        sorted.sort(Task::compareTo);
        return sorted;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Maintains heap property by moving element up.
     */
    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            
            if (heap.get(index).compareTo(heap.get(parentIndex)) >= 0) {
                break;
            }

            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    /**
     * Maintains heap property by moving element down.
     */
    private void bubbleDown(int index) {
        int size = heap.size();

        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int highest = index;

            if (leftChild < size && 
                heap.get(leftChild).compareTo(heap.get(highest)) < 0) {
                highest = leftChild;
            }

            if (rightChild < size && 
                heap.get(rightChild).compareTo(heap.get(highest)) < 0) {
                highest = rightChild;
            }

            if (highest == index) {
                break;
            }

            swap(index, highest);
            index = highest;
        }
    }

    /**
     * Swaps two elements in the heap and updates the index map.
     */
    private void swap(int i, int j) {
        Task temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);

        idToIndexMap.put(heap.get(i).getId(), i);
        idToIndexMap.put(heap.get(j).getId(), j);
    }
}
