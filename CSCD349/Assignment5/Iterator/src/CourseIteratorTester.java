import course.Course;
import course.Student;

import java.util.Iterator;

public class CourseIteratorTester {
    public static void main(String[] args) {
        Student[] s = {new Student("Jim", 3.9), new Student("bob", 3.5), new Student("Lilly", 4.0),
        new Student("Alex", 9001.1), new Student("Lars", -4.283917)};
        Course course = new Course(s);
        for(Object o : course){
            System.out.println(o.toString());
        }
        Iterator i = course.iterator();
        while(i.hasNext()){
            System.out.println(i.next().toString());
        }
    }
}
