import java.util.List;

public class Student {

    private String firstName;
    private String lastName;
    private String group;
    private List<Subject> subjects;
    private float average;

    public Student(){
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getAverage() {
        return average;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        String marks="";
        for (Subject subject:subjects){
            marks+=subject.toString()+"\n";
        }
        return firstName+" "+lastName+" "+group+"\n"+marks+"Average: "+average;
    }
}
