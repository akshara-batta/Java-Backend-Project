package service;

import model.*;
import database.DBConnection;
import java.sql.*;

public class UserServiceImpl implements UserService{

    private boolean emailExists(String email) throws SQLException{

        String sql = "select * from users where email = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, email);

                try(ResultSet rs = ps.executeQuery()){
                    return rs.next();
                }
            }
    }

    private void insertUser(User user) throws SQLException{

        String sql = "insert into users values(?, ?, ?, ?, ?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

                ps.setInt(1, user.getUserId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPasswordHash());
                ps.setString(5, user.getRole());

                ps.executeUpdate();
            }
    }

    @Override 
    public Student registerStudent(int userId, String name, String email, String passwordHash){

        try{
            if(emailExists(email)){
                return null;
            }

            Student student = new Student(userId, name, email, passwordHash);

            insertUser(student);
            
            return student;
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override 
    public User login(String email, String passwordHash){
        
        String sql = "select * from users where email = ? and password_hash = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

                ps.setString(1, email);
                ps.setString(2, passwordHash);

                try(ResultSet rs = ps.executeQuery()){

                    if(rs.next()){

                        int id = rs.getInt("user_id");
                        String name = rs.getString("name");
                        String password = rs.getString("password_hash");
                        String role = rs.getString("role");

                        switch(role){
                            case "STUDENT":
                                return new Student(id, name, email, passwordHash);

                            case "ADMIN":
                                return new Admin(id, name, email, passwordHash);

                            case "AUTHORITY" :
                                return new Authority(id, name, email, passwordHash);
                        }
                    }
                }
            }
            catch(SQLException e){
                throw new RuntimeException(e);
            }
            return null;
    }

    @Override
    public Admin createAdmin(int userId, String name, String email, String passwordHash){

        try{
            if(emailExists(email)){
                return null;
            }

            Admin admin = new Admin(userId, name, email, passwordHash);

            insertUser(admin);

            return admin;
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Authority createAuthority(int userId, String name, String email, String passwordHash){
        
        try{
            if(emailExists(email)){
                return null;
            }

            Authority authority = new Authority(userId, name, email, passwordHash);

            insertUser(authority);

            return authority;
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Authority getAuthorityById(int userId){
        
        String sql = "select * from users where user_id = ? and role = 'AUTHORITY'";

        try(Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
                
                ps.setInt(1, userId);

                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return new Authority(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"), rs.getString("password_hash"));
                    }
                }
             }

             catch(SQLException e){
                throw new RuntimeException(e);
             }
             return null;
    }
}
