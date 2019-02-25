package logics;

import java.io.Serializable;
import java.util.UUID;

abstract class SuperTask implements Serializable {
    protected String uuid;
    protected String title;
    protected String description;
    protected TaskStatus taskStatus;

    public String getUuid() {
        return  uuid;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public SuperTask(String title, String description) {
        this.uuid = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        taskStatus = TaskStatus.InProgress;
    }

    @Override
    public String toString() {
        return title;
    }
}

