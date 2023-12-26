package toy.toyproject2.exception;

public class CanNotCancelOrderException extends RuntimeException{
    public CanNotCancelOrderException() {
        super();
    }

    public CanNotCancelOrderException(String message) {
        super(message);
    }

    public CanNotCancelOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CanNotCancelOrderException(Throwable cause) {
        super(cause);
    }
}
