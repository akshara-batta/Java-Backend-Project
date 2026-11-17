package service;
import model.Student;
import model.User;

public interface UserService {

    public Student registerStudent(int userId, String name, String email, String passwordHash);

    public User login(String email, String password);
}
