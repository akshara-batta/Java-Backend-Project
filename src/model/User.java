package model;

public abstract class User {

    private int userId;
    private String name;
    private String email;
    private String passwordHash;

    public User(int userId, String name, String email, String passwordHash){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public int getUserId(){
        return userId;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }

    public abstract String getRole();

}
