package course;

import java.util.ArrayList;
import java.util.Iterator;

public class Course implements Iterable {
    private ArrayList<Student> students;
    public Course(Student[] s){
        students = new ArrayList<>();
        for(Student n : s)
            this.students.add(n);
    }
    @Override
    public CourseIterator iterator(){
        return new CourseIterator();
    }

    private class CourseIterator implements Iterator{
        private int pos = -1;
        @Override
        public boolean hasNext(){
            try {
                pos++;
                return students.get(pos) != null;
            }catch(IndexOutOfBoundsException e){
                return false;
            }
        }
        @Override
        public Student next(){
                return students.get(pos);
        }
        @Override
        public void remove(){
            throw new UnsupportedOperationException("Remove is not available");
        }

    }
}
