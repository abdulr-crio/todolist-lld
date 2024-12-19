package service;

import entity.Task;
import model.Status;
import model.Tag;
import model.enums.SortBy;
import model.response.TaskResponse;
import repository.TaskActivityRepository;
import repository.TaskRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskAuditService taskAuditService;

    public TaskService(TaskRepository taskRepository, TaskAuditService taskAuditService) {
        this.taskRepository = taskRepository;
        this.taskAuditService = taskAuditService;
    }

    public String createTask(String taskName, String taskDescription, List<Tag> tags, String deadline) {
        Optional<Task> alreadyExistedTask = taskRepository.findTaskByName(taskName);
        if (alreadyExistedTask.isPresent()
                && !(Status.COMPLETED.equals(alreadyExistedTask.get().getStatus()))) {
            return "TASK_ALREADY_EXISTS";
        }
        Task task = taskRepository.saveTask(taskName, taskDescription, tags, deadline, Status.OPEN);
        taskAuditService.auditTask(task.getTaskId(), "CREATED");
        return "TASK_ADDED " + task.getTaskId();
    }

    public String modifyTask(Long taskId, String taskDescription, List<Tag> tags, String deadline) {
        Optional<Task> task = taskRepository.findTaskById(taskId);
        if (task.isEmpty()) {
            return "TASK_NOT_EXIST";
        }
        Task currentTask = task.get();
        if (Objects.nonNull(taskDescription) && !taskDescription.equals("null")) {
            currentTask.setTaskDescription(taskDescription);
        }
        if (Objects.nonNull(tags)) {
            currentTask.setTags(tags);
        }
        if (Objects.nonNull(deadline) && !deadline.equals("null")) {
            currentTask.setDeadline(deadline);
        }
        Task updatedTask = taskRepository.updateTask(taskId, currentTask);
        taskAuditService.auditTask(updatedTask.getTaskId(), "MODIFIED");
        return "TASK_MODIFIED " + updatedTask.getTaskId();
    }

    public String changeTaskStatus(Long taskId, Status status) {
        Optional<Task> task = taskRepository.findTaskById(taskId);
        if (task.isEmpty()) {
            return "task does not exist";
        }
        Task currentTask = task.get();
        currentTask.setStatus(status);
        Task updatedTask = taskRepository.updateTask(taskId, currentTask);
        taskAuditService.auditTask(updatedTask.getTaskId(), "STATUS_CHANGED");
        return "TASK_STATUS " + updatedTask.getTaskName() + " " + updatedTask.getStatus();
    }

    public String getTasks(Status filterBy, SortBy sortBy) {
        List<Task> tasks = taskRepository.getTasks();
        if (SortBy.DESC.equals(sortBy)) {
            tasks.sort(Comparator.comparing(Task::getDeadline).reversed());
        } else {
            tasks.sort(Comparator.comparing(Task::getDeadline));
        }
        List<TaskResponse> taskResponses = new ArrayList<>();
        tasks.forEach(
                task -> {
                    taskResponses.add(
                            new TaskResponse(
                                    task.getTaskId(), task.getTaskName(), task.getStatus()));
                });
        List<String> results;
        if (Objects.isNull(filterBy)) {
            results = taskResponses.stream().map(TaskResponse::getTaskName).collect(Collectors.toList());
        } else {
            results = taskResponses.stream().filter(x ->  (x.getStatus().equals(filterBy))).map(TaskResponse::getTaskName).collect(Collectors.toList());
        }

        return String.join(",", results);
    }

    public String getTask(Long taskId) {
        Optional<Task> task = taskRepository.findTaskById(taskId);
        if(task.isEmpty()) {
            return "TASK_NOT_EXISTS";
        }
        return task.get().getTaskName();
    }

    public String removeTask(Long taskId) {
        Optional<Task> task = taskRepository.findTaskById(taskId);
        if(task.isEmpty()) {
            return "TASK_NOT_EXISTS";
        }
        taskRepository.deleteTask(taskId);
        taskAuditService.auditTask(taskId, "REMOVED");
        return "TASK_REMOVED " + task.get().getTaskName();
    }
}
