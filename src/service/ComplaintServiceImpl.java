package service;
import model.Complaint;
import model.ComplaintCategory;
import model.ComplaintStatus;
import java.util.*;

public class ComplaintServiceImpl implements ComplaintService{
    
    private ArrayList<Complaint> complaints = new ArrayList<>();

    private int nextComplaintId = 1001;

    @Override
    public Complaint submitComplaint(int studentId, ComplaintCategory category, String description){

        Complaint complaint = new Complaint(nextComplaintId++, studentId, category, description);

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
        
        return complaints;
    }

    @Override
    public void assignAuthority(int complaintId, int authorityId){

        for(Complaint i: complaints){
            if(i.getComplaintId() == complaintId){
                i.setAuthorityId(authorityId);
                break;
            }
        }
    }

    @Override
    public void updateComplaintStatus(int complaintId, ComplaintStatus status){

        for(Complaint i: complaints){
            if(i.getComplaintId() == complaintId){
                i.setStatus(status);
                break;
            }
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
    public void reopenComplaint(int complaintId){
        
        for(Complaint i: complaints){
            if((i.getComplaintId() == complaintId) && (i.getComplaintStatus() == ComplaintStatus.RESOLVED)){
                i.setStatus(ComplaintStatus.REOPENED);
                break;
            }
        }
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
