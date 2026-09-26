package Strategy.payment;

import Strategy.PricingStrategy;
import model.Reservation;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DefaultPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculerTotalPrice(BigDecimal roomPrice,LocalDate chickin , LocalDate chickout){
        BigDecimal total = BigDecimal.ZERO;
        long nombreNuit = ChronoUnit.DAYS.between(chickin,chickout);
        LocalDate date = chickin;
        while (date.isBefore(chickout)){
            BigDecimal nightPrice = roomPrice;
            if(date.getMonthValue() == 7 || date.getMonthValue() == 8 )
            {
                nightPrice = nightPrice.multiply(new BigDecimal("1.30"));
            }
            if(date.getMonthValue() == 11 || date.getMonthValue() == 12 ||
                    date.getMonthValue() == 1 || date.getMonthValue() == 2 )
            {
                nightPrice = nightPrice.multiply(new BigDecimal("0.85"));
            }
            if(date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY ){
                nightPrice = nightPrice.multiply(new BigDecimal("1.15"));
            }

            total = total.add(nightPrice);

            date = date.plusDays(1);

        }
        if (nombreNuit >= 14) {
            total = total.multiply(new BigDecimal("0.85"));

        } else if (nombreNuit >= 7) {
            total = total.multiply(new BigDecimal("0.90"));
        }

        long daysBefCheckIn = ChronoUnit.DAYS.between(LocalDate.now(),chickin);
        if(daysBefCheckIn >= 30){
            total = total.multiply(new BigDecimal("0.95"));
        }
        else if(daysBefCheckIn <= 3){
            total = total.multiply(new BigDecimal("1.10"));
        }
        return total;
    }

}