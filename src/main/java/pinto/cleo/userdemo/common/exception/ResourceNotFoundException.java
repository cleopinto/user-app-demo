package pinto.cleo.userdemo.common.exception;

/**
 * Created by cleo on 5/5/18.
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
