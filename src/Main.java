import model.*;
import service.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void studentDashboard(Student student, Scanner sc, ComplaintService complaintService){

        int choice;
        
        do {

        System.out.println("\n===========STUDENT DASHBOARD===========");
        System.out.println("Welcome to the Student Dashboard, " + student.getName());
        System.out.println("1. Submit Complaint");
        System.out.println("2. View My Complaints");
        System.out.println("3. Cancel Complaint");
        System.out.println("4. Logout");

        System.out.print("Enter your Choice: ");
        choice = sc.nextInt();
        sc.nextLine();

        switch(choice){

            case 1: {
                System.out.println("\n====== SUBMIT COMPLAINT ======");
                System.out.println("Select Complaint Category:");
                
                System.out.println("1. Electricity");
                System.out.println("2. Water");
                System.out.println("3. Mess");
                System.out.println("4. Cleanliness");
                System.out.println("5. Hostel");
                
                System.out.print("Enter your choice: ");
                int categoryChoice = sc.nextInt();
                sc.nextLine(); 
                
                ComplaintCategory category = null;

        switch(categoryChoice) {

            case 1:
                category = ComplaintCategory.ELECTRICITY;
                break;
            case 2:
                category = ComplaintCategory.WATER;
                break;
            case 3:
                category = ComplaintCategory.MESS;
                break;
            case 4:
                category = ComplaintCategory.CLEANLINESS;
                break;
            case 5:
                category = ComplaintCategory.HOSTEL;
                break;
            default:
                System.out.println("Invalid category choice!");
            }
            if(category==null){
                break;
            }
            ArrayList<ComplaintSubcategory> subcategories = new ArrayList<>();

            for(ComplaintSubcategory sub : ComplaintSubcategory.values()) { 
                if(sub.getCategory() == category) {
                    subcategories.add(sub);
                }
            }
            System.out.println("\nSelect Complaint Subcategory:");

            for(int i = 0; i < subcategories.size(); i++) {
                System.out.println((i + 1) + ". " + subcategories.get(i).getDisplayName());
            }

            System.out.print("Enter your choice: ");
            int subcategoryChoice = sc.nextInt();
            sc.nextLine();

            if(subcategoryChoice < 1 || subcategoryChoice > subcategories.size()) {
                System.out.println("Invalid subcategory choice!");
                break;
            }

            ComplaintSubcategory subcategory = subcategories.get(subcategoryChoice - 1);

            System.out.print("Enter Complaint Description: ");
            String description = sc.nextLine();

            Complaint complaint = complaintService.submitComplaint(
                student.getUserId(),
                category,
                subcategory,
                description
            );

            System.out.println("\nComplaint submitted successfully!");
            System.out.println("Your Complaint ID: " + complaint.getComplaintId());
            System.out.println("Status: " + complaint.getComplaintStatus());

            break;
        }
                
            case 2: {
                ArrayList<Complaint> myComplaints =
                complaintService.getStudentComplaints(student.getUserId());

                if(myComplaints.isEmpty()) {
                    System.out.println("\nNo complaints found!");
                }
                else {
                    System.out.println("\n====== MY COMPLAINTS ======");

                for(Complaint c : myComplaints) {
                    System.out.println("\nComplaint ID: " + c.getComplaintId());
                    System.out.println("Category: " + c.getComplaintCategory());
                    System.out.println("Subcategory: " + c.getSubcategory().getDisplayName());
                    System.out.println("Description: " + c.getDescription());
                    System.out.println("Status: " + c.getComplaintStatus());
                    System.out.println("-----------------------------");
                    }
                }

                break;
            }

            case 3: {
                System.out.print("\nEnter Complaint ID to cancel: ");
                int complaintId = sc.nextInt();
                sc.nextLine();

                boolean cancelled = complaintService.cancelComplaint(student.getUserId(), complaintId);

                if(cancelled) {
                    System.out.println("Complaint cancelled successfully!");
                }
                else {
                    System.out.println("Unable to cancel complaint.");
                    System.out.println("Check the ID, ownership, or complaint status.");
                }

                break;
            }

            case 4:
                System.out.println("Logging out...");
                break;

            default:
                System.out.println("Invalid choice. Try Again!");
        }
            
        } 
        while (choice!=4);
        
    }
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserService userService = new UserServiceImpl();

        ComplaintService complaintService = new ComplaintServiceImpl();

        int choice;

        do{
            System.out.println("\n==========RESOLVE360==========");
            System.out.println("1. Register as Student");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1: {
                    System.out.println("===========STUDENT REGISTRATION==========");

                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter your email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter your password: ");
                    String hashPassword = sc.nextLine();

                    Student student = userService.registerStudent(userId, name, email, hashPassword);

                    if(student!=null){
                        System.out.println("Registeration Successfull!");
                        System.out.println("Welcome, " + student.getName());
                    }
                    else{
                        System.out.println("Email already Registered!");
                    }
                    break;
                }
                case 2: {
                    System.out.println("==========Login==========");

                    System.out.print("Enter your email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter password: ");
                    String hashPassword = sc.nextLine();

                    User loggedIn = userService.login(email, hashPassword);

                    if(loggedIn!=null){
                        System.out.println("Login Successful!");
                        System.out.println("Welcome, " + loggedIn.getName());
                        System.out.println("Role: " + loggedIn.getRole());

                        if(loggedIn instanceof Student){
                            studentDashboard((Student) loggedIn, sc, complaintService);
                        }
                    }
                    else{
                        System.out.println("Login unsuccessful! Invalid email or password!");
                    }
                    break;
                }
                case 3:
                    System.out.println("Thankyou for using RESOLVE360");
                    break;
                default:
                    System.out.println("Wrong Choice. Try Again");
            }
        }
        while(choice!=3);

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

        /* 

        STUDENT SERVICE

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

        STUDENT SERVICE
        */

        /*

        ADMIN AND AUTHORITY SERVICE

        Admin admin = userService.createAdmin(201, "System Admin", "admin@gmail.com", "admin123");

        Authority authority = userService.createAuthority(301, "Maintainence Team", "authority@gmail.com", "authority123");

        System.out.println(admin.getRole());

        System.out.println(authority.getRole());

        User adminLogin = userService.login("admin@gmail.com", "admin123");

        System.out.println(adminLogin.getEmail());

        System.out.println(admin.getRole());

        ADMIN AND AUTHORITY SERVICE

        */
    }
}
