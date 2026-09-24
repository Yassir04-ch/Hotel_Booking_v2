package Strategy.payment;

import exception.InvalidBalanceException;
import model.User;

import java.math.BigDecimal;

public interface PaymentStrategy {

    void pay(User user, BigDecimal amount) throws InvalidBalanceException;
}