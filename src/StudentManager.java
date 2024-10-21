import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentManager {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void printStudent(Student student) {
        System.out.println(student.toString());
    }

    public void viewListStudent(List<Student> students) {
        students.forEach(this::printStudent);
    }

    public Map<Object, Object> getAllStudents() {
        Map<Object, Object> resultExecuted = new HashMap<>();
        String query = "SELECT * FROM students";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                long studentCode = rs.getLong("student_code");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                String sex = rs.getString("sex");

                Student student = new Student(id, name, studentCode, age, address, sex);
                resultExecuted.put(id, student);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to fetch students: ", e);
            e.printStackTrace();
        }
        return resultExecuted;
    }

    public Map<Object, Object> addStudent(Student student) {
        Map<Object, Object> resultExecuted = new HashMap<>();
        String query = "INSERT INTO students (id, name, student_code, age, address, sex) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query); {
                UUID id = UUID.randomUUID();
                stmt.setString(1, id.toString());
                stmt.setString(2, student.getName());
                stmt.setLong(3, student.getStudentCode());
                stmt.setInt(4, student.getAge());
                stmt.setString(5, student.getAddress());
                stmt.setString(6, student.getSex());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    resultExecuted.put("Noti", "Successfully added student");
                    logger.info("Student added: " + student);
                } else {
                    resultExecuted.put("Noti", "Failed to add student");
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add student: ", e);
            resultExecuted.put("error", e.getMessage());
        }
        return resultExecuted;
    }

    public Map<Object, Object> updateStudent(Student student) {
        Map<Object, Object> resultExecuted = new HashMap<>();
        String query = "UPDATE students SET name = ?, student_code = ?, age = ?, address = ?, sex = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query); {

                stmt.setString(1, student.getName());
                stmt.setLong(2, student.getStudentCode());
                stmt.setInt(3, student.getAge());
                stmt.setString(4, student.getAddress());
                stmt.setString(5, student.getSex());
                stmt.setString(6, student.getId().toString());
            }

            int rowAffected = stmt.executeUpdate();
            if(rowAffected > 0) {
                resultExecuted.put("Noti", "Successfully updated student");
                logger.info("Student updated: " + student);
            } else {
                resultExecuted.put("Noti", "Failed to update student, ID may not exist");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to update student: ", e);
            resultExecuted.put("error", e.getMessage());
        }
        return resultExecuted;
    }

    public Map<Object, Object> deleteStudent(Student student) {
        Map<Object, Object> resultExecuted = new HashMap<>();
        String query = "DELETE FROM students WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query); {
                stmt.setString(1, student.getId().toString());
                int rowAffected = stmt.executeUpdate();
                if(rowAffected > 0) {
                    resultExecuted.put("Noti", "Successfully deleted student");
                    logger.info("Student deleted: " + student);
                } else {
                    resultExecuted.put("Noti", "Failed to delete student");
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to delete student: ", e);
            resultExecuted.put("error", e.getMessage());
        }
        return resultExecuted;
    }

}
