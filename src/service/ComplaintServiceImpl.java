package service;
import model.Complaint;
import model.ComplaintCategory;
import model.ComplaintStatus;
import model.ComplaintSubcategory;
import model.Authority;
import java.util.ArrayList;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ComplaintNotFoundException;
import exceptions.InvalidComplaintStatusException;
import exceptions.UnauthorizedComplaintAccessException;

public class ComplaintServiceImpl implements ComplaintService{

    private int getNextComplaintId(){

        String sql = "select coalesce(max(complaint_id), 1000) + 1 from complaints";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

                rs.next();
                return rs.getInt(1);
            }
            catch(SQLException e){
                throw new RuntimeException(e);
            }
    }

    private Complaint mapComplaint(ResultSet rs) throws SQLException {

        int authorityId = rs.getInt("authority_id");

        if(rs.wasNull()){
            authorityId = -1;
        }

        Complaint complaint = new Complaint(rs.getInt("complaint_id"), rs.getInt("student_id"), ComplaintCategory.valueOf(rs.getString("category")), ComplaintSubcategory.valueOf(rs.getString("subcategory")), rs.getString("description"));

        complaint.setStatus(ComplaintStatus.valueOf(rs.getString("status")));

        complaint.setAuthorityId(authorityId);

        return complaint;
    }

    private UserService userService;

    public ComplaintServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public Complaint submitComplaint(int studentId, ComplaintCategory category, ComplaintSubcategory subcategory, String description){

        if(category == null || subcategory == null || description == null || description.trim().isEmpty()){
            throw new IllegalArgumentException("Complaint details cannot be empty");
        }

        if(subcategory.getCategory() != category){
            throw new IllegalArgumentException("Invalid SUBCATEGORY for chosen CATEGORY");
        }

        int complaintId = getNextComplaintId();

        Complaint complaint = new Complaint(complaintId, studentId, category, subcategory, description);

        String sql = "insert into complaints values (?, ?, ?, ?, ?, ?, ?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, complaint.getComplaintId());
            ps.setInt(2, complaint.getStudentId());
            ps.setString(3, category.name());
            ps.setString(4, subcategory.name());
            ps.setString(5, complaint.getComplaintStatus().name());
            ps.setString(6, complaint.getDescription());
            ps.setNull(7, java.sql.Types.INTEGER); 
            
            ps.executeUpdate();
            return complaint;
            }

        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Complaint> getStudentComplaints(int studentId){

        ArrayList<Complaint> studentComplaint = new ArrayList<>();

        String sql = "select * from complaints where student_id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

                ps.setInt(1, studentId);

                try(ResultSet rs = ps.executeQuery()){

                    while(rs.next()){
                        studentComplaint.add(mapComplaint(rs));
                    }
                }
            }

            catch(SQLException e){
                throw new RuntimeException(e);
            }
        return studentComplaint;
    }

    @Override
    public Complaint getComplaintById(int complaintId){

        String sql = "select * from complaints where complaint_id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

                ps.setInt(1, complaintId);

                try(ResultSet rs = ps.executeQuery()){

                    if(rs.next()){

                        return mapComplaint(rs);
                    }
                }
            }
            catch(SQLException e){
                throw new RuntimeException(e);
            }

        return null;
    }

    @Override
    public ArrayList<Complaint> getAllComplaints(){
        
        ArrayList<Complaint> allComplaints = new ArrayList<>();

        String sql = "select * from complaints";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                allComplaints.add(mapComplaint(rs));
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
        return allComplaints;
    }

    @Override
    public void assignAuthority(int complaintId, int authorityId){

        if(authorityId <=0){
            throw new IllegalArgumentException("Invalid authority ID!");
        }

        Authority authority = userService.getAuthorityById(authorityId);

        if(authority == null){
            throw new IllegalArgumentException("Authority doesnt exist!");
        }

        String sql = "update complaints set authority_id = ? where complaint_id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

                ps.setInt(1, authorityId);
                ps.setInt(2, complaintId);

                int rows = ps.executeUpdate();

                if(rows == 0){
                    throw new ComplaintNotFoundException("Complaint ID: " + complaintId + " not found!");
                }
            }
            catch(SQLException e){
                throw new RuntimeException(e);
            }
    }

    @Override
    public void updateComplaintStatus(int complaintId, int authorityId, ComplaintStatus status){

        Complaint complaint = getComplaintById(complaintId);

        if(complaint == null){
            throw new ComplaintNotFoundException("Complaint ID: " + complaintId  + " not found!");
        }

        if(complaint.getAuthorityId() != authorityId){
            throw new UnauthorizedComplaintAccessException("This complaint is not assigned to you!");
        }

        if(status == null){
            throw new IllegalArgumentException("Invalid status");
        }

        ComplaintStatus currentStatus = complaint.getComplaintStatus();

        if(!((currentStatus == ComplaintStatus.PENDING || currentStatus == ComplaintStatus.REOPENED) && status == ComplaintStatus.IN_PROGRESS) && !(currentStatus == ComplaintStatus.IN_PROGRESS && status == ComplaintStatus.RESOLVED)){
            throw new InvalidComplaintStatusException("Invalid status transition " + currentStatus  + " to " + status);
        }

        String sql = "update complaints set status = ? where complaint_id = ?";

        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, status.name());
        ps.setInt(2, complaintId);

        ps.executeUpdate();

    } catch(SQLException e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public ArrayList<Complaint> getAssignedComplaints(int authorityId){

        ArrayList<Complaint> authorityComplaints = new ArrayList<>();

        String sql = "select * from complaints where authority_id = ?";

        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, authorityId);

        try(ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                authorityComplaints.add(mapComplaint(rs));
            }
        }

        } catch(SQLException e) {
        throw new RuntimeException(e);
        }

        return authorityComplaints;
    }

    @Override
    public int getComplaintCountByStatus(ComplaintStatus status){

        if(status == null){
            throw new IllegalArgumentException("Status cannot be null!");
        }

        String sql = "select count(*) from complaints where status = ?";

        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, status.name());

            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public void reopenComplaint(int studentId, int complaintId){

        Complaint complaint = getComplaintById(complaintId);

        if(complaint == null){
            throw new ComplaintNotFoundException("Complaint ID: " + complaintId + " not found!");
        }

        if(complaint.getStudentId() != studentId){
            throw new UnauthorizedComplaintAccessException("You can only reopen your own complaints!");
        }

        if(complaint.getComplaintStatus()!= ComplaintStatus.RESOLVED){
            throw new InvalidComplaintStatusException("Only resolved complaints can be reopened!");
        }
        
        String sql = "update complaints set status = ? where complaint_id = ?";

        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, ComplaintStatus.REOPENED.name());
        ps.setInt(2, complaintId);

        ps.executeUpdate();

    } catch(SQLException e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public boolean cancelComplaint(int studentId, int complaintId){

        String sql = "update complaints set status = ? " + 
                     "where complaint_id = ? and student_id = ? and status = ?";

        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, ComplaintStatus.CANCELLED.name());
        ps.setInt(2, complaintId);
        ps.setInt(3, studentId);
        ps.setString(4, ComplaintStatus.PENDING.name());

        int rows = ps.executeUpdate();

        return rows > 0;

    } catch(SQLException e) {
        throw new RuntimeException(e);
    }
    }
}
