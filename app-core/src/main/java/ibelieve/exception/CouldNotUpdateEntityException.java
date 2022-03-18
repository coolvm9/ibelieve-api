package ibelieve.exception;

public class CouldNotUpdateEntityException extends IllegalStateException{
    public CouldNotUpdateEntityException(String message){
        super(message);
    }
}
