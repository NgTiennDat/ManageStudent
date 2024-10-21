import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class StudentUI {
    private final StudentManager manager;
    private final Scanner scanner = new Scanner(System.in);

    public StudentUI(StudentManager manager) {
        this.manager = manager;
    }

    public void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Student");
            System.out.println("2. Show All Students");
            System.out.println("3. Edit Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            switch (scanner.nextLine()) {
                case "1" -> addStudentUI();
                case "2" -> showStudentsUI();
                case "3" -> updateStudentUI();
                case "4" -> deleteStudentUI();
                case "5" -> {
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStudentUI() {
        Student student = inputStudentData(UUID.randomUUID());
        String result = manager.addStudent(student);
        System.out.println(result);
    }

    private void showStudentsUI() {
        List<Student> students = manager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private void updateStudentUI() {
        System.out.print("Enter Student ID to Update: ");
        UUID id = inputUUID();
        if (id == null) return;

        Student student = inputStudentData(id);
        String result = manager.updateStudent(student);
        System.out.println(result);
    }

    private void deleteStudentUI() {
        System.out.print("Enter Student ID to Delete: ");
        UUID id = inputUUID();
        if (id == null) return;

        String result = manager.deleteStudent(id);
        System.out.println(result);
    }

    private UUID inputUUID() {
        try {
            return UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
            return null;
        }
    }

    private Student inputStudentData(UUID id) {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Student Code: ");
        long studentCode = scanner.nextLong();

        System.out.print("Enter Student Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Student Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Student Sex: ");
        String sex = scanner.nextLine();

        return new Student(id, name, studentCode, age, address, sex);
    }
}
