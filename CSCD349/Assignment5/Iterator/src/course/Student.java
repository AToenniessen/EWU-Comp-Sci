package course;

public class Student {
    private String name;
    private double GPA;
    public Student(String s, double g){
        this.name = s;
        this.GPA = g;

    }
    @Override
    public String toString(){
        return "Student name: " + this.name + " and their GPA is " + this.GPA;
    }
}
