package az.sattarhadjiev.mstransfer.exception.custom;

public class NotEnoughBalanceException extends RuntimeException {
    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
