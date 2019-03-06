package logics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Task extends  SuperTask{
    private LocalDate deadline;
    private String tags;
    private List<String> splittedTags;

    public List<String> getSplittedTags() {
        return splittedTags;
    }

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
        splittedTags = Arrays.stream(tags.split(" ")).collect(Collectors.toList());
    }



}
