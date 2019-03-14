package nk.parking.fees;

import nk.parking.currency.CurrencyBaseRates;
import nk.parking.customer.CustomerType;
import nk.parking.meter.Meter;
import nk.parking.meter.MeterRepository;
import nk.parking.meter.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Optional;

@Service
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;
    private final MeterRepository meterRepository;

    @Override
    public Fee save(Fee fee) {
        return feeRepository.save(fee);
    }

    @Override
    public Optional<Fee> findById(Long id) {
        return feeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        feeRepository.deleteById(id);
    }

    @Override
    public void calculateFee(Meter meter) {
        if (meter.getFee() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This meter already has a calculated fee");
        } else {
            Fee fee = new Fee();
            CustomerType customerType = meter.getCustomer().getCustomerType();
            switch (customerType) {
                case REGULAR:
                    fee.setAmount(calculateAmountForRegular(meter));
                    break;
                case DISABLED:
                    fee.setAmount(calculateAmountForDisabled(meter));
                    break;
                default:
                    fee.setAmount(calculateAmountForRegular(meter));
            }
            fee.setMeter(meter);
            fee.setCurrency(meter.getCustomer().getCurrency());
            feeRepository.save(fee);
        }
    }

    private BigDecimal calculateAmountForRegular(Meter meter) {
        long minutes = Duration.between(meter.getStartTime(), meter.getEndTime()).toMinutes();
        BigDecimal amount;
        if (minutes <= 60) {
            amount = CurrencyBaseRates.REG_FIRST_HOUR;
        } else if (minutes <= 120) {
            amount = CurrencyBaseRates.REG_FIRST_HOUR.add(CurrencyBaseRates.REG_SECOND_HOUR);
        } else {
            long thirdRateMinutes = minutes - 120;
            double thirdRateHours = (double) thirdRateMinutes / 60;
            amount = CurrencyBaseRates.REG_FIRST_HOUR
                    .add(CurrencyBaseRates.REG_SECOND_HOUR)
                    .add(CurrencyBaseRates.REG_THIRD_AND_NEXT_HOURS
                            .multiply(BigDecimal.valueOf(Math.ceil(thirdRateHours))));
        }
        return amount.divide(meter.getCustomer().getCurrency().getRate(), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateAmountForDisabled(Meter meter) {
        long minutes = Duration.between(meter.getStartTime(), meter.getEndTime()).toMinutes();
        BigDecimal amount;
        if (minutes <= 60) {
            amount = CurrencyBaseRates.DIS_FIRST_HOUR;
        } else if (minutes <= 120) {
            amount = CurrencyBaseRates.DIS_FIRST_HOUR.add(CurrencyBaseRates.DIS_SECOND_HOUR);
        } else {
            long thirdRateMinutes = minutes - 120;
            double thirdRatehours = (double) thirdRateMinutes / 60;
            amount = CurrencyBaseRates.DIS_FIRST_HOUR.add(CurrencyBaseRates.DIS_SECOND_HOUR)
                    .add(CurrencyBaseRates.DIS_THIRD_AND_NEXT_HOURS
                            .multiply(BigDecimal.valueOf(Math.ceil(thirdRatehours))));
        }
        return amount.divide(meter.getCustomer().getCurrency().getRate(), 2, RoundingMode.HALF_UP);
    }

    @Autowired
    public FeeServiceImpl(FeeRepository feeRepository, MeterRepository meterRepository) {
        this.feeRepository = feeRepository;
        this.meterRepository = meterRepository;
    }
}
