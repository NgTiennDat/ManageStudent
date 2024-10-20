import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class StudentUI {
    private final StudentManager manager;
    private final Scanner scanner;

    public StudentUI(StudentManager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
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

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addStudentUI();
                    break;
                case "2":
                    showStudentsUI();
                    break;
                case "3":
                    updateStudentUI();
                    break;
                case "4":
//                    deleteStudentUI();
                    break;
                case "5":
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addStudentUI() {
        System.out.println("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Student Code: ");
        long studentCode = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter Student age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Student address: ");
        String address = scanner.nextLine();

        System.out.println("Enter Student Sex: ");
        String sex = scanner.nextLine();

        UUID id = UUID.randomUUID();
        Student student = new Student(id, name, studentCode, age, address, sex);
        Map<Object, Object> result = manager.addStudent(student);
        System.out.println(result.get("Noti"));
    }

    public void showStudentsUI() {
        Map<Object, Object> students = manager.getAllStudents();

        if(students.isEmpty()) {
            System.out.println("No students found");
        } else {
            students.forEach((id, student) -> {
               System.out.println(student);
            });
        }
    }
    private void updateStudentUI() {
        System.out.print("Enter Student ID to Update: ");
        String idStr = scanner.nextLine();
        UUID id;

        try {
            id = UUID.fromString(idStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
            return;
        }

        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter New Student Code: ");
        long studentCode = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter New Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter New Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter New Sex: ");
        String sex = scanner.nextLine();

        Student student = new Student(id, name, studentCode, age, address, sex);
        Map<Object, Object> result = manager.updateStudent(student);
        System.out.println(result.get("Noti"));
    }

}
