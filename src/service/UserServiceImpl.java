package service;

import model.Admin;
import model.Authority;
import model.Student;
import model.User;
import java.util.*;

public class UserServiceImpl implements UserService{
    
    ArrayList<User> users = new ArrayList<>();

    @Override 
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

    @Override 
    public User login(String email, String passwordHash){
        for(User i: users){
            if((i.getEmail().equals(email)) && i.getPasswordHash().equals(passwordHash)){
                return i;
            }
        }
        return null;
    }

    @Override
    public Admin createAdmin(int userId, String name, String email, String passwordHash){

        for(User i: users){
            if(i.getEmail().equals(email)){
                return null;
            }
        }

        Admin admin = new Admin(userId, name, email, passwordHash);

        users.add(admin);

        return admin;
    }

    @Override
    public Authority createAuthority(int userId, String name, String email, String passwordHash){
        for(User i: users){
            if(i.getEmail().equals(email)){
                return null;
            }
        }

        Authority authority = new Authority(userId, name, email, passwordHash);

        users.add(authority);

        return authority;
    }

    @Override
    public Authority getAuthorityById(int userId){
        for(User u : users){
            if(u instanceof Authority && u.getUserId() == userId){
                return (Authority) u;
            }
        }
        return null;
    }
}
