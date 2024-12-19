package service;

import entity.Task;
import model.Status;
import repository.TaskRepository;

import java.util.List;

public class TaskStatisticsService {
    private final TaskRepository taskRepository;

    public TaskStatisticsService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public String getStatistics() {
        List<Task> completedTasks = taskRepository.getTasks(Status.COMPLETED);
        List<Task> openTasks = taskRepository.getTasks(Status.OPEN);
        List<Task> inProgressTasks = taskRepository.getTasks(Status.IN_PROGRESS);
        return "OPEN:"+openTasks.size()+","+"IN_PROGRESS:"+inProgressTasks.size()+","+"COMPLETED:"+completedTasks.size();
    }
}
