package exceptions;

public class InvalidComplaintStatusException extends RuntimeException{
    public InvalidComplaintStatusException(String message){
        super(message);
    }
}
