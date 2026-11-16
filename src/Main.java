import model.*;
import service.*;

public class Main {
    
    public static void main(String[] args) {
        
        ComplaintService service = new ComplaintServiceImpl();

        /*COMPLAINT SERVICE!

        Complaint c1 = service.submitComplaint(101, ComplaintCategory.ELECTRICITY, "Power outage in hostel");

        Complaint c2 = service.submitComplaint(102, ComplaintCategory.WATER, "No Water Supply");

        System.out.println(c1.getComplaintId());

        System.out.println(c2.getComplaintId());

        System.out.println(service.getStudentComplaints(103).size());

        service.assignAuthority(1001, 201);

        System.out.println(c1.getAuthorityId());

        service.updateComplaintStatus(1001, ComplaintStatus.IN_PROGRESS);

        System.out.println(c1.getComplaintStatus());

        System.out.println(service.getAssignedComplaints(201).size());

        System.out.println(service.getComplaintCountByStatus(ComplaintStatus.PENDING));

        System.out.println(service.getComplaintCountByStatus(ComplaintStatus.IN_PROGRESS));

        service.updateComplaintStatus(1001, ComplaintStatus.RESOLVED);

        service.reopenComplaint(1001);

        System.out.println(c1.getComplaintStatus());

        System.out.println(service.cancelComplaint(102, 1002));

        System.out.println(c2.getComplaintStatus());

        COMPLAINT SERVICE!

        */
 
    }
}
