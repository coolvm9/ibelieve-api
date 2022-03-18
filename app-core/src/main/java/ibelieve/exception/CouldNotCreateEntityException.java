package ibelieve.exception;

public class CouldNotCreateEntityException extends IllegalStateException{
    public CouldNotCreateEntityException(String message){
        super(message);
    }
}
