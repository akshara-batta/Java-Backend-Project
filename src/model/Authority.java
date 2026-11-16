package model;

public class Authority extends User{
    
    public Authority(int userId, String name, String email, String passwordHash){
        super(userId, name, email, passwordHash);
    }

    @Override
    public String getRole(){
        return "AUTHORITY";
    }
}
