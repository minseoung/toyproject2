package toy.toyproject2.exception;

public class DuplicatedLonginIdException extends RuntimeException{
    public DuplicatedLonginIdException() {
        super();
    }

    public DuplicatedLonginIdException(String message) {
        super(message);
    }

    public DuplicatedLonginIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedLonginIdException(Throwable cause) {
        super(cause);
    }
}
