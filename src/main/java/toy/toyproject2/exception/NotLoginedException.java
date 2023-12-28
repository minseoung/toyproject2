package toy.toyproject2.exception;

public class NotLoginedException extends RuntimeException{
    public NotLoginedException() {
        super();
    }

    public NotLoginedException(String message) {
        super(message);
    }

    public NotLoginedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLoginedException(Throwable cause) {
        super(cause);
    }
}
