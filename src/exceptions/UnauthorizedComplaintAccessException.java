package exceptions;

public class UnauthorizedComplaintAccessException extends RuntimeException{
    public UnauthorizedComplaintAccessException(String message){
        super(message);
    }
}