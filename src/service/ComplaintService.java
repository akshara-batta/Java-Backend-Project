package service;
import model.Complaint;
import model.ComplaintCategory;
import model.ComplaintStatus;

import java.util.*;

public interface ComplaintService {
    
    public Complaint submitComplaint(int studentId, ComplaintCategory category, String description);

    public ArrayList<Complaint> getStudentComplaints(int studentId);

    public Complaint getComplaintById(int complaintId);

    public ArrayList<Complaint> getAllComplaints();

    public void assignAuthority(int complaintId, int authorityId);

    public void updateComplaintStatus(int complaintId, ComplaintStatus status);

    public ArrayList<Complaint> getAssignedComplaints(int authorityId);

    public int getComplaintCountByStatus(ComplaintStatus status);

    public void reopenComplaint(int complaintId);

    public boolean cancelComplaint(int studentId, int complaintId);
}
