import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void printStudent(Student student) {
        System.out.println(student.toString());
    }

    public void viewListStudent(List<Student> students) {
        students.forEach(this::printStudent);
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

}
