package ibelieve.exception;

public class CouldNotDeleteEntityException extends IllegalStateException{
    public CouldNotDeleteEntityException(String message){
        super(message);
    }
}
