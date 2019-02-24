package logics;

public class SubTask extends SuperTask{

    //порядковый номер в рамках задачи
    private int index;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public SubTask(String title, String description) {
        super(title, description);
    }
}
