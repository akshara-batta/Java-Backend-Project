package service;
import model.Complaint;
import model.ComplaintCategory;
import model.ComplaintStatus;
import model.ComplaintSubcategory;

import java.util.*;

public interface ComplaintService {
    
    public Complaint submitComplaint(int studentId, ComplaintCategory category, ComplaintSubcategory subcategory, String description);

    public ArrayList<Complaint> getStudentComplaints(int studentId);

    public Complaint getComplaintById(int complaintId);

    public ArrayList<Complaint> getAllComplaints();

    public void assignAuthority(int complaintId, int authorityId);

    public void updateComplaintStatus(int complaintId, int authorityId, ComplaintStatus status);

    public ArrayList<Complaint> getAssignedComplaints(int authorityId);

    public int getComplaintCountByStatus(ComplaintStatus status);

    public void reopenComplaint(int studentId, int complaintId);

    public boolean cancelComplaint(int studentId, int complaintId);
}
