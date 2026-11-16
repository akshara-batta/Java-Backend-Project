package model;

public class Admin extends User {
    
    public Admin(int userId, String name, String email, String passwordHash){
        super(userId, name, email, passwordHash);
    }

    @Override
    public String getRole(){
        return "ADMIN";
    }
}
