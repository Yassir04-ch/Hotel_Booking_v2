package main;

import utils.InputUtils;

public class PaymentMenu {
    public static int menuPayment() {
        System.out.println("1. Balance");
        System.out.println("2. Carte bancaire");
        return InputUtils.readInt("Entrer votre choix : ");
    }
}