package Strategy.payment;

import Strategy.PaymentStrategy;
import exception.InvalidBalanceException;
import model.User;

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