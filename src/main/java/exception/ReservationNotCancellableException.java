package exception;

public class ReservationNotCancellableException extends Exception {

    public ReservationNotCancellableException(String message) {
        super(message);
    }
}