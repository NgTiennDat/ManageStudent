import java.util.UUID;

public class Student {
    private UUID id;
    private String name;
    private long studentCode;
    private int age;
    private String address;
    private String sex;

    public Student() {}

    public Student(UUID id, String name, long studentCode, int age, String address, String sex) {
        this.id = id;
        this.name = name;
        this.studentCode = studentCode;
        this.age = age;
        this.address = address;
        this.sex = sex;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(long studentCode) {
        this.studentCode = studentCode;
    }

    @Override
    public String toString() {
        return "Student {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studentCode=" + studentCode +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
