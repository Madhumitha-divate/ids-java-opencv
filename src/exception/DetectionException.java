package exception;

public class DetectionException extends Exception {

    public DetectionException(String message) {
        super(message);
    }

    public DetectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
