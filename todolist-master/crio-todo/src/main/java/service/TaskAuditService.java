package service;

import repository.TaskActivityRepository;

import java.sql.Timestamp;
import java.util.List;

public class TaskAuditService {
    private final TaskActivityRepository taskActivityRepository;

    public TaskAuditService(TaskActivityRepository taskActivityRepository) {
        this.taskActivityRepository = taskActivityRepository;
    }

    public void auditTask(Long taskId, String taskAction) {
        taskActivityRepository.addActivityLog(taskId, taskAction);
    }
    public List<String> getTaskLogs() {
        return taskActivityRepository.getActivityLogs();
    }
}
