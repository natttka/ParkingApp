package nk.parking.currency;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public enum Currency {
    PLN,
    USD,
    EUR;

    private static final BigDecimal PLN_RATE = BigDecimal.ONE;
    private static final BigDecimal EUR_RATE = new BigDecimal(4.3);
    private static final BigDecimal USD_RATE = new BigDecimal(3.8);

    @JsonCreator
    public static Currency fromStringValue(String value) {
        switch (value) {
            case "PLN": return Currency.PLN;
            case "USD": return Currency.USD;
            case "EUR": return Currency.EUR;
            default: return Currency.PLN;
        }
    }

    public BigDecimal getRate() {
        switch (this) {
            case PLN: return PLN_RATE;
            case EUR: return EUR_RATE;
            case USD: return USD_RATE;
            default: return PLN_RATE;
        }
    }
}
