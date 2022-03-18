package ibelieve.exception;

public class EntityDoesNotExistException extends  IllegalArgumentException{
    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
