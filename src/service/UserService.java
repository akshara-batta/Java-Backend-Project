package service;
import model.*;

public interface UserService {

    public Student registerStudent(int userId, String name, String email, String passwordHash);

    public User login(String email, String password);

    public Admin createAdmin(int userId, String name, String email, String passwordHash);

    public Authority createAuthority(int userId, String name, String email, String passwordHash);
}
