package logics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Task extends  SuperTask{
    private LocalDate deadline;
    private String tags;

    public LocalDate getDeadline() {
        return deadline;
    }
    public String getTags() {
        return tags;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<SubTask> subTasks = new ArrayList<>();


    public Task(String title, String description, LocalDate deadline, String tags) {
        super(title, description);
        this.deadline = deadline;
        this.tags = tags;
    }



}
