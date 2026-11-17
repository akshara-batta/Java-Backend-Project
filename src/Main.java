import model.*;
import service.*;

public class Main {
    
    public static void main(String[] args) {

        /*COMPLAINT SERVICE!
        
        ComplaintService service = new ComplaintServiceImpl();

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

        UserService userService = new UserServiceImpl();

        Student s1 = userService.registerStudent(101, "Akshara", "aksharabatta@gmail.com", "aksh123");

        Student s2 = userService.registerStudent(102, "Blehh", "aksharabatta@gmail.com", "aksh123");

        Student s3 = userService.registerStudent(103, "Riya", "riya@gmail.com", "pass789");

        User loggedIn = userService.login("aksharabatta@gmail.com", "aksh123");

        if(loggedIn!=null){

            System.out.println(loggedIn.getName());

            System.out.println(loggedIn.getRole());

        }

        User wrongIn = userService.login("aksharabatta@gmail.com", "w123");

        if(wrongIn!=null){

        System.out.println(wrongIn.getName());

        System.out.println(wrongIn.getRole());

        }
        else{
            System.out.println("Invalid email or password");
        }

        if(s2!=null){
            System.out.println(s2.getName());
        }
        else{
            System.out.println("Email already registered");
        }

        System.out.println(s3.getName());

        System.out.println(s3.getEmail());
    }
}
