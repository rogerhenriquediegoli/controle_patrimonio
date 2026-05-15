package src.exception;

public class AppException extends RuntimeException {

    private final boolean askForPermission;

    public AppException(String message) {
        this(message, false);
    }

    public AppException(String message, boolean askForPermission) {
        super(message);
        this.askForPermission = askForPermission;
    }

    public boolean isAskForPermission() {
        return askForPermission;
    }
}