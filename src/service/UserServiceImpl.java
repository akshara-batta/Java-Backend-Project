package service;

import model.Student;
import model.User;
import java.util.*;

public class UserServiceImpl implements UserService{
    
    ArrayList<User> users = new ArrayList<>();

    public Student registerStudent(int userId, String name, String email, String passwordHash){

        for(User i: users){
            if(i.getEmail().equals(email)){
                return null;
            }
        }

        Student student = new Student(userId, name, email, passwordHash);

        users.add(student);

        return student;
    }

    public User login(String email, String passwordHash){
        for(User i: users){
            if((i.getEmail().equals(email)) && i.getPasswordHash().equals(passwordHash)){
                return i;
            }
        }
        return null;
    }
}
