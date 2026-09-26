package Strategy.payment;

import Strategy.PaymentStrategy;
import model.User;
import utils.InputUtils;

import java.math.BigDecimal;

public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public void pay(User user, BigDecimal amount) {

        System.out.println("Paiement par carte bancaire");
        InputUtils.readString("Entrer votre cart Bancaire :");
        System.out.println("Paiement par carte effectué avec succès.");
    }
}
