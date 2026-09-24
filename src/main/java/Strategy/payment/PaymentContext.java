package Strategy.payment;

import exception.InvalidBalanceException;
import model.User;
import Strategy.payment.PaymentStrategy;

import java.math.BigDecimal;

public class PaymentContext {

    private PaymentStrategy paymentStrategy;

    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void pay(User user, BigDecimal amount) throws InvalidBalanceException {
        paymentStrategy.pay(user, amount);
    }
}