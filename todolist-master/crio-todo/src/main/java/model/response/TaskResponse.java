package model.response;

import lombok.Data;
import model.Status;

@Data
public class TaskResponse {
    private final Long taskId;
    private final String taskName;
    private final Status status;
}
