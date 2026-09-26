package exception;

public class PaymentNotFound extends  Exception{
    public PaymentNotFound(String message){
        super(message);
    }
}
