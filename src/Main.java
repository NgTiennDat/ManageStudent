import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        UUID id = UUID.randomUUID();
        Student student = new Student(id, "Dat", 123456789L, 21, "Hanoi", "Male");
        Map<Object, Object> result = studentService.addStudent(student);
        System.out.println(result);
    }
}
