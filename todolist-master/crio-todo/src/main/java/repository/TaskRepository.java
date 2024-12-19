package repository;

import entity.Task;
import model.Status;
import model.Tag;

import java.util.*;
import java.util.stream.Collectors;

public class TaskRepository {

    private final Map<Long, Task> database = new HashMap<>();
    private Long autoIncrementId = 1L;

    public Task saveTask(
            String taskName, String taskDescription, List<Tag> tags, String deadline, Status status) {
        Task task = new Task(autoIncrementId, taskName, taskDescription, tags, deadline, status);
        database.put(autoIncrementId, task);
        autoIncrementId++;
        return task;
    }

    public Task updateTask(Long taskId, Task task) {
        database.put(taskId, task);
        return task;
    }

    public Optional<Task> findTaskById(Long taskId) {
        return database.values().stream().filter(x -> x.getTaskId().equals(taskId)).findFirst();
    }

    public Optional<Task> findTaskByName(String taskName) {
        return database.values().stream().filter(x -> x.getTaskName().equals(taskName)).findFirst();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(database.values());
    }

    public List<Task> getTasks(Status status) {
        return database.values().stream()
                .filter(x -> x.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public void deleteTask(Long taskId) {
        database.remove(taskId);
    }
}
