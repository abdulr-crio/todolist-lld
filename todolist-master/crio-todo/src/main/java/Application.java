import model.Status;
import model.Tag;
import model.enums.SortBy;
import repository.TaskActivityRepository;
import repository.TaskRepository;
import service.TaskAuditService;
import service.TaskService;
import service.TaskStatisticsService;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        System.out.println("Welcome to todo application ......");

        TaskRepository taskRepository = new TaskRepository();
        TaskActivityRepository taskActivityRepository = new TaskActivityRepository();
        TaskAuditService taskAuditService = new TaskAuditService(taskActivityRepository);
        TaskService taskService = new TaskService(taskRepository, taskAuditService);
        TaskStatisticsService taskStatisticsService = new TaskStatisticsService(taskRepository);

        // ADD_TASK
        String taskOne =
                taskService.createTask(
                        "LLD-1", "SDE-2 Problem statements", List.of(Tag.ENGINEERING, Tag.PRODUCT), "2023-09-20");
        System.out.println(taskOne);

        String taskTwo =
                taskService.createTask(
                        "LLD-2", "UI/UX", List.of(Tag.DESIGN, Tag.PRODUCT), "2023-09-12");
        System.out.println(taskTwo);

        String taskThree =
                taskService.createTask(
                        "LLD-3", "SDE-3 Problem statements", List.of(Tag.ENGINEERING), "2023-09-13");
        System.out.println(taskThree);

        String taskFour =
                taskService.createTask(
                        "MENTOR", "product management", List.of(Tag.PRODUCT), "2023-09-14");
        System.out.println(taskFour);

        //GET_TASK
        System.out.println(taskService.getTask(1L));
        System.out.println(taskService.getTask(2L));
        System.out.println(taskService.getTask(3L));
        System.out.println(taskService.getTask(4L));
        System.out.println(taskService.getTask(5L));

        // MODIFY_TASK
        String modifiedTaskOne = taskService.modifyTask(1L, null, null, "2023-09-20");
        System.out.println(modifiedTaskOne);

        String modifiedTaskTwo = taskService.modifyTask(2L, "UI/UX FIGMA", null, "2023-09-20");
        System.out.println(modifiedTaskTwo);

        String modifiedTaskThree = taskService.modifyTask(3L, "Engineering Problem Statement", null, null);
        System.out.println(modifiedTaskThree);

        String modifiedTaskFour = taskService.modifyTask(4L, null, List.of(Tag.PRODUCT, Tag.ENGINEERING), null);
        System.out.println(modifiedTaskFour);

        //REMOVE_TASK
        System.out.println(taskService.removeTask(1L));

        // CHANGE_TASK_STATUS <taskId> <status>
        System.out.println(taskService.changeTaskStatus(2L, Status.IN_PROGRESS));
        System.out.println(taskService.changeTaskStatus(3L, Status.COMPLETED));
        System.out.println(taskService.changeTaskStatus(4L, Status.COMPLETED));

        // LIST_TASK
        System.out.println(taskService.getTasks(Status.COMPLETED, SortBy.ASC));
        System.out.println(taskService.getTasks(null, null));

        //GET_ACTIVITY_LOG
        List<String> activityLogs = taskAuditService.getTaskLogs();
        System.out.println(String.join(",", activityLogs));

        //GET_STATISTICS
        System.out.println(taskStatisticsService.getStatistics());

    }
}
