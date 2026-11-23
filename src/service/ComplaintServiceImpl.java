package service;
import model.Complaint;
import model.ComplaintCategory;
import model.ComplaintStatus;
import model.ComplaintSubcategory;
import model.Authority;
import java.util.*;

import exceptions.ComplaintNotFoundException;
import exceptions.InvalidComplaintStatusException;
import exceptions.UnauthorizedComplaintAccessException;

public class ComplaintServiceImpl implements ComplaintService{

    private UserService userService;

    public ComplaintServiceImpl(UserService userService){
        this.userService = userService;
    }
    
    private ArrayList<Complaint> complaints = new ArrayList<>();

    private int nextComplaintId = 1001;

    private Complaint findComplaint(int complaintId){
        for(Complaint c: complaints){
            if(c.getComplaintId() == complaintId){
                return c;
            }
        }
        throw new ComplaintNotFoundException("Complaint ID: " + complaintId + " not found!");
    }

    @Override
    public Complaint submitComplaint(int studentId, ComplaintCategory category, ComplaintSubcategory subcategory, String description){

        if(category == null || subcategory == null || description == null || description.trim().isEmpty()){
            throw new IllegalArgumentException("Complaint details cannot be empty");
        }

        if(subcategory.getCategory() != category){
            throw new IllegalArgumentException("Invalid SUBCATEGORY for chosen CATEGORY");
        }

        Complaint complaint = new Complaint(nextComplaintId++, studentId, category, subcategory, description);

        complaints.add(complaint);

        return complaint;
    }

    @Override
    public ArrayList<Complaint> getStudentComplaints(int studentId){

        ArrayList<Complaint> studentComplaint = new ArrayList<>();
        for(Complaint i: complaints){
            if(i.getStudentId() == studentId){
                studentComplaint.add(i);
            }
        }
        return studentComplaint;
    }

    @Override
    public Complaint getComplaintById(int complaintId){

        for(Complaint i: complaints){
            if(i.getComplaintId() == complaintId){
                return i;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Complaint> getAllComplaints(){
        
        return new ArrayList<>(complaints);
    }

    @Override
    public void assignAuthority(int complaintId, int authorityId){

        Complaint complaint = findComplaint(complaintId);

        if(authorityId <=0){
            throw new IllegalArgumentException("Invalid authority ID!");
        }

        Authority authority = userService.getAuthorityById(authorityId);

        if(authority == null){
            throw new IllegalArgumentException("Authority doesnt exist!");
        }

        complaint.setAuthorityId(authorityId);
    }

    @Override
    public void updateComplaintStatus(int complaintId, int authorityId, ComplaintStatus status){

        Complaint complaint = findComplaint(complaintId);

        if(complaint.getAuthorityId() != authorityId){
            throw new UnauthorizedComplaintAccessException("This complaint is not assigned to you!");
        }

        if(status == null){
            throw new IllegalArgumentException("Invalid status");
        }

        ComplaintStatus currentStatus = complaint.getComplaintStatus();

        if((currentStatus == ComplaintStatus.PENDING || currentStatus == ComplaintStatus.REOPENED) && status == ComplaintStatus.IN_PROGRESS){
            complaint.setStatus(status);
        }
        else if(currentStatus == ComplaintStatus.IN_PROGRESS && status == ComplaintStatus.RESOLVED){
            complaint.setStatus(status);
        }
        else{
            throw new InvalidComplaintStatusException("Invalid status transition " + currentStatus + " to " + status);
        }
    }

    @Override
    public ArrayList<Complaint> getAssignedComplaints(int authorityId){

        ArrayList<Complaint> authorityComplaints = new ArrayList<>();

        for(Complaint i: complaints){
            if(i.getAuthorityId() == authorityId){
                authorityComplaints.add(i);
            }
        }
        return authorityComplaints;
    }

    @Override
    public int getComplaintCountByStatus(ComplaintStatus status){

        int countStatus = 0;

        for(Complaint i:complaints){
            if(i.getComplaintStatus() == status){
                countStatus++;
            }
        }

        return countStatus;
    }

    @Override
    public void reopenComplaint(int studentId, int complaintId){

        Complaint complaint = findComplaint(complaintId);

        if(complaint.getStudentId() != studentId){
            throw new UnauthorizedComplaintAccessException("You can only reopen your own complaints!");
        }

        if(complaint.getComplaintStatus()!= ComplaintStatus.RESOLVED){
            throw new InvalidComplaintStatusException("Only resolved complaints can be reopened!");
        }
        
        complaint.setStatus(ComplaintStatus.REOPENED);
    }

    @Override
    public boolean cancelComplaint(int studentId, int complaintId){

        for(Complaint i: complaints){
            if((i.getStudentId() == studentId) && (i.getComplaintId() == complaintId) && i.getComplaintStatus() == ComplaintStatus.PENDING){
                i.setStatus(ComplaintStatus.CANCELLED);
                return true;
            }
        }
        return false;
    }
}
