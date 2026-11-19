package model;

public class Complaint {
    
    private int complaintId;
    private int studentId;
    private ComplaintCategory category;
    private ComplaintSubcategory subcategory;
    private ComplaintStatus status;
    private String description;
    private int authorityId;

    public Complaint(int complaintId, int studentId, ComplaintCategory category,ComplaintSubcategory subcategory, String description){
        this.complaintId = complaintId;
        this.studentId = studentId;
        this.category = category;
        this.subcategory = subcategory;
        this.status = ComplaintStatus.PENDING;
        this.description = description;
        this.authorityId = -1;
    }

    public int getComplaintId(){
        return complaintId;
    }

    public int getStudentId(){
        return studentId;
    }

    public ComplaintCategory getComplaintCategory(){
        return category;
    }

    public ComplaintSubcategory getSubcategory(){
        return subcategory;
    }

    public ComplaintStatus getComplaintStatus(){
        return status;
    }

    public String getDescription(){
        return description;
    }

    public int getAuthorityId(){
        return authorityId;
    }

    public void setAuthorityId(int authorityId){
        this.authorityId = authorityId;
    }

    public void setStatus(ComplaintStatus status){
        this.status = status;
    }
}
