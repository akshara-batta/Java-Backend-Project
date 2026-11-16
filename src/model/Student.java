package model;

public class Student extends User {
    
    public Student(int userId, String name, String email, String passwordHash){
        super(userId, name, email, passwordHash);
    }

    @Override
    public String getRole(){
        return "STUDENT";
    }
}
