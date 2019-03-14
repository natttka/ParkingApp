package nk.parking.currency;

import java.math.BigDecimal;

public class CurrencyBaseRates {
    public static final BigDecimal REG_FIRST_HOUR = new BigDecimal(1);
    public static final BigDecimal REG_SECOND_HOUR = new BigDecimal(2);
    public static final BigDecimal REG_THIRD_AND_NEXT_HOURS = REG_SECOND_HOUR.multiply(new BigDecimal(1.5));
    public static final BigDecimal DIS_FIRST_HOUR = BigDecimal.ZERO;
    public static final BigDecimal DIS_SECOND_HOUR = new BigDecimal(2);
    public static final BigDecimal DIS_THIRD_AND_NEXT_HOURS = DIS_SECOND_HOUR.multiply(new BigDecimal(1.2));
}
