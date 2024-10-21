import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentManager {
    private final Logger logger = Logger.getLogger(StudentManager.class.getName());

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getLong("student_code"),
                        rs.getInt("age"),
                        rs.getString("address"),
                        rs.getString("sex")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch students: ", e);
        }
        return students;
    }

    public String addStudent(Student student) {
        String query = "INSERT INTO students (id, name, student_code, age, address, sex) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, student.getId().toString());
            stmt.setString(2, student.getName());
            stmt.setLong(3, student.getStudentCode());
            stmt.setInt(4, student.getAge());
            stmt.setString(5, student.getAddress());
            stmt.setString(6, student.getSex());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Successfully added student" : "Failed to add student";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add student: ", e);
            return "Error: " + e.getMessage();
        }
    }

    public String updateStudent(Student student) {
        if (student.getId() == null) {
            return "Student id is null";
        }
        if(student.getName() == null || student.getName().isEmpty()) {
            return "Student name is null";
        }
        if(student.getAge() < 0) {
            return "Student age is negative";
        }

        String query = "UPDATE students SET name = ?, student_code = ?, age = ?, address = ?, sex = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, student.getName());
            stmt.setLong(2, student.getStudentCode());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getAddress());
            stmt.setString(5, student.getSex());
            stmt.setString(6, student.getId().toString());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Successfully updated student" : "Failed to update student, ID may not exist";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to update student: ", e);
            return "Error: " + e.getMessage();
        }
    }

    public String deleteStudent(UUID id) {
        String query = "DELETE FROM students WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "Successfully deleted student" : "Failed to delete student, ID may not exist";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to delete student: ", e);
            return "Error: " + e.getMessage();
        }
    }

    public List<Student> sortStudentByName() {
        List<Student> students = this.getAllStudents();
        students.sort(Comparator.comparing(Student::getName).reversed());
        return students;
    }

}
