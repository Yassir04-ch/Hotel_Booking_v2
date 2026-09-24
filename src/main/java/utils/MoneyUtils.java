package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {

    public static BigDecimal add(BigDecimal amount1, BigDecimal amount2) {
        return amount1.add(amount2).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal subtract(BigDecimal balance, BigDecimal amount) {
        return balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiply(BigDecimal amount, BigDecimal factor) {
        return amount.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean chekckAmount(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public static boolean isPositive(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static BigDecimal calculateTTC(BigDecimal amount) {
        BigDecimal tva = amount.multiply(new BigDecimal("0.20"));

        return amount.add(tva).setScale(2, RoundingMode.HALF_UP);
    }

}