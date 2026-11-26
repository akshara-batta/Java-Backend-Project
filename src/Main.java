import model.*;
import service.*;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.ComplaintNotFoundException;
import exceptions.InvalidComplaintStatusException;
import exceptions.UnauthorizedComplaintAccessException;

public class Main {

    public static int readInt(Scanner sc) {
        while(true) {
            if(sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            }
            else {
                System.out.print("Invalid input! Please enter a number: ");
                sc.nextLine();
            }
        }
    }

    public static void studentDashboard(Student student, Scanner sc, ComplaintService complaintService){

        int choice;
        
        do {

        System.out.println("\n===========STUDENT DASHBOARD===========");
        System.out.println("Welcome to the Student Dashboard, " + student.getName());
        System.out.println("1. Submit Complaint");
        System.out.println("2. View My Complaints");
        System.out.println("3. Cancel Complaint");
        System.out.println("4. Reopen Complaint");
        System.out.println("5. Logout");

        System.out.print("Enter your Choice: ");
        choice = readInt(sc);

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
                int categoryChoice = readInt(sc); 
                
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
            int subcategoryChoice = readInt(sc);

            if(subcategoryChoice < 1 || subcategoryChoice > subcategories.size()) {
                System.out.println("Invalid subcategory choice!");
                break;
            }

            ComplaintSubcategory subcategory = subcategories.get(subcategoryChoice - 1);

            System.out.print("Enter Complaint Description: ");
            String description = sc.nextLine();

            try{

            Complaint complaint = complaintService.submitComplaint(
                student.getUserId(),
                category,
                subcategory,
                description
            );

            System.out.println("\nComplaint submitted successfully!");
            System.out.println("Your Complaint ID: " + complaint.getComplaintId());
            System.out.println("Status: " + complaint.getComplaintStatus());
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

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
                int complaintId = readInt(sc);

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

            case 4: {

                System.out.print("Enter Complaint ID to Reopen: ");
                int complaintId = readInt(sc);

                try{
                    complaintService.reopenComplaint(student.getUserId(), complaintId);
                
                System.out.println("Complaint reopened successfully!");
                }

                catch(ComplaintNotFoundException | UnauthorizedComplaintAccessException | InvalidComplaintStatusException e){
                    System.out.println(e.getMessage());
                }

                break;
            }

            case 5:
                System.out.println("Logging out...");
                break;

            default:
                System.out.println("Invalid choice. Try Again!");
        }
            
        } 
        while (choice!=5);
        
    }

    public static void adminDashboard(Admin admin, Scanner sc, ComplaintService complaintService, UserService userService){

    int choice;

    do {
        System.out.println("\n========== ADMIN DASHBOARD ==========");
        System.out.println("Welcome, " + admin.getName());

        System.out.println("1. View All Complaints");
        System.out.println("2. Assign Complaint to Authority");
        System.out.println("3. Create Authority Account");
        System.out.println("4. View Complaint Statistics");
        System.out.println("5. Logout");

        System.out.print("Enter your choice: ");
        choice = readInt(sc);

        switch(choice){

            case 1: {
                ArrayList<Complaint> allComplaints = complaintService.getAllComplaints();

                if(allComplaints.isEmpty()) {
                    System.out.println("\nNo complaints have been submitted yet!");
                }
                else {
                    System.out.println("\n========== ALL COMPLAINTS ==========");

                for(Complaint c : allComplaints) {
                    System.out.println("\nComplaint ID: " + c.getComplaintId());
                    System.out.println("Student ID: " + c.getStudentId());
                    System.out.println("Category: " + c.getComplaintCategory());
                    System.out.println("Subcategory: " + c.getSubcategory().getDisplayName());
                    System.out.println("Description: " + c.getDescription());
                    System.out.println("Status: " + c.getComplaintStatus());
                    System.out.println("Assigned Authority ID: " + c.getAuthorityId());

                    System.out.println("-----------------------------------");
                    }
                }

                break;
            }

            case 2: {
                System.out.print("\nEnter Complaint ID: ");
                int complaintId = readInt(sc);

                System.out.print("\nEnter Authority ID: ");
                int authorityId = readInt(sc);

                try{
                    complaintService.assignAuthority(complaintId, authorityId);

                    System.out.println("Complaint assigned successfully!");
                }
                catch(ComplaintNotFoundException | IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }

                break;
            }
                

            case 3:
                System.out.println("\n=========CREATE AUTHORITY ACCOUNT=========");

                    System.out.print("Enter Authority ID: ");
                    int authorityId = readInt(sc);

                    System.out.print("Enter Authority Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Authority Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Password: ");
                    String password = sc.nextLine();

                    Authority authority = userService.createAuthority(authorityId, name, email, password);

                    if(authority!=null){
                        System.out.println("\nAuthority account created successfully!");
                        System.out.println("Authority ID: " + authority.getUserId());
                        System.out.println("Name: " + authority.getName());
                    }
                    else{
                        System.out.println("An account with this email already exists.");
                    }
                    break;

            case 4: {

                int pending = complaintService.getComplaintCountByStatus(ComplaintStatus.PENDING);
                int inProgress = complaintService.getComplaintCountByStatus(ComplaintStatus.IN_PROGRESS);
                int resolved = complaintService.getComplaintCountByStatus(ComplaintStatus.RESOLVED);
                int reopened = complaintService.getComplaintCountByStatus(ComplaintStatus.REOPENED);
                int cancelled = complaintService.getComplaintCountByStatus(ComplaintStatus.CANCELLED);

                int total = pending + inProgress + resolved + reopened + cancelled;

                System.out.println("\n===== COMPLAINT STATISTICS =====");
                System.out.println("Total Complaints: " + total);
                System.out.println("Pending: " + pending);
                System.out.println("In Progress: " + inProgress);
                System.out.println("Resolved: " + resolved);
                System.out.println("Reopened: " + reopened);
                System.out.println("Cancelled: " + cancelled);
                break;
            }

            case 5:
                System.out.println("Logging out...");
                break;

            default:
                System.out.println("Invalid choice. Try Again!");
        }

    } while(choice != 5);
}
    
    public static void authorityDashboard(Authority authority, Scanner sc, ComplaintService complaintService){

        int choice;

        do{
            System.out.println("\n========== AUTHORITY DASHBOARD ==========");
            System.out.println("Welcome, " + authority.getName());

            System.out.println("1. View Assigned Complaints");
            System.out.println("2. Update Complaint Status");
            System.out.println("3. Logout");

            System.out.print("Enter your choice: ");
            choice = readInt(sc);

            switch(choice){
                
                case 1: {

                    ArrayList<Complaint> assignedComplaints = complaintService.getAssignedComplaints(authority.getUserId());

                    if(assignedComplaints.isEmpty()){
                        System.out.println("\nNo Complaints assigned to you yet!");
                    }
                    else{
                        System.out.println("\n====== YOUR ASSIGNED COMPLAINTS ======");

                        for(Complaint c : assignedComplaints) {
                            System.out.println("\nComplaint ID: " + c.getComplaintId());
                            System.out.println("Student ID: " + c.getStudentId());
                            System.out.println("Category: " + c.getComplaintCategory());
                            System.out.println("Subcategory: " + c.getSubcategory().getDisplayName());
                            System.out.println("Description: " + c.getDescription());
                            System.out.println("Status: " + c.getComplaintStatus());
                            System.out.println("-----------------------------------");
                        }
                    }

                    break;
                }
                    

                case 2: {
                    System.out.print("\nEnter Complaint ID: ");
                    int complaintId = readInt(sc);

                    System.out.println("\nSelect New Status:");
                    System.out.println("1. In Progress");
                    System.out.println("2. Resolved");

                    System.out.print("Enter your choice: ");
                    int statusChoice = readInt(sc);

                    ComplaintStatus newStatus = null;

                    switch(statusChoice) {
                        case 1:
                            newStatus = ComplaintStatus.IN_PROGRESS;
                            break;

                        case 2:
                            newStatus = ComplaintStatus.RESOLVED;
                            break;

                        default:
                            System.out.println("Invalid status choice!");
                            break;
                    }

                    Complaint complaint = complaintService.getComplaintById(complaintId);

                    if(complaint != null){
                        
                        try{
                            complaintService.updateComplaintStatus(complaintId, authority.getUserId(), newStatus);
                            System.out.println("Complaint status updated successfully!");
                        }
                        catch(ComplaintNotFoundException | UnauthorizedComplaintAccessException | InvalidComplaintStatusException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    
                    break;
                }

                case 3:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice. Try Again!");
            }
        }
        while(choice !=3);
    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserService userService = new UserServiceImpl();

        ComplaintService complaintService = new ComplaintServiceImpl(userService);

        userService.createAdmin(1, "System Admin", "admin@resolve360.com", "admin123");

        userService.createAuthority(2, "Electricity Department", "electricity@resolve360.com", "authority123");

        int choice;

        do{
            System.out.println("\n==========NESTFIX==========");
            System.out.println("1. Register as Student");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            choice = readInt(sc);

            switch(choice){
                case 1: {
                    System.out.println("===========STUDENT REGISTRATION==========");

                    System.out.print("Enter User ID: ");
                    int userId = readInt(sc);

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
                        else if(loggedIn instanceof Admin){
                            adminDashboard((Admin) loggedIn, sc, complaintService, userService);
                        }
                        else if(loggedIn instanceof Authority){
                            authorityDashboard((Authority) loggedIn, sc, complaintService);
}
                    }
                    else{
                        System.out.println("Login unsuccessful! Invalid email or password!");
                    }
                    break;
                }
                case 3:
                    System.out.println("Thankyou for using NESTFIX");
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