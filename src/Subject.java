public class Subject {

    private String title;
    private int mark;

    public Subject(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return title+": "+Integer.toString(mark);
    }
}
