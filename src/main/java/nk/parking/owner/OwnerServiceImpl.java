package nk.parking.owner;

import nk.parking.fees.Fee;
import nk.parking.fees.FeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final FeeRepository feeRepository;

    public OwnerServiceImpl(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    @Override
    public Double totalAmountDaily(LocalDateTime from, LocalDateTime to) {
        Double total = 0.0;
        for (Fee fee : feeRepository.findAll()) {
            if (fee.getMeter().getEndTime().isAfter(from) && fee.getMeter().getEndTime().isBefore(to)) {
                total += fee.getAmount().doubleValue();
            }
        }
        return total;
    }
}
