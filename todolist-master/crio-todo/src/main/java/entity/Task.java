package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Status;
import model.Tag;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private List<Tag> tags;
    private String deadline;
    private Status status;

    public Task deepCopy() {
        Task copiedTask = new Task();
        copiedTask.setTaskId(taskId);
        copiedTask.setTaskName(taskName);
        copiedTask.setTaskDescription(taskDescription);
        copiedTask.setTags(tags);
        copiedTask.setDeadline(deadline);
        copiedTask.setStatus(status);
        return copiedTask;
    }
}
