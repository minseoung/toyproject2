package toy.toyproject2.exception;

public class NotExistMemberException extends RuntimeException{
    public NotExistMemberException() {
        super();
    }

    public NotExistMemberException(String message) {
        super(message);
    }

    public NotExistMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistMemberException(Throwable cause) {
        super(cause);
    }
}
