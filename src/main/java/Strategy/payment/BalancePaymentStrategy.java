package Strategy.payment;

import model.User;
import utils.MoneyUtils;
import exception.InvalidBalanceException;

import java.math.BigDecimal;

public class BalancePaymentStrategy implements PaymentStrategy {

    @Override
    public void pay(User user, BigDecimal amount) throws InvalidBalanceException {

        if (!MoneyUtils.chekckAmount(user.getBalance(), amount)) {
            throw new InvalidBalanceException("Votre balance est insuffisante.");
        }

        BigDecimal newBalance = MoneyUtils.subtract(user.getBalance(), amount);

        user.setBalance(newBalance);
    }
}