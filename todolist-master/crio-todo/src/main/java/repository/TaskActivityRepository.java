package repository;

import java.util.ArrayList;
import java.util.List;

public class TaskActivityRepository {
    private final List<String> activityLogs = new ArrayList<>();


    public void addActivityLog(Long taskId, String taskAction) {
        String activity = taskId + ":" + taskAction;
        activityLogs.add(activity);
    }

    public List<String> getActivityLogs() {
        return activityLogs;
    }
}
