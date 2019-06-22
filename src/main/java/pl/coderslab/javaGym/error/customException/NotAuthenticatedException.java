package pl.coderslab.javaGym.error.customException;

public class NotAuthenticatedException extends RuntimeException {

    private String message = "*Not authenticated exception.";

    public NotAuthenticatedException() {}

    public NotAuthenticatedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}