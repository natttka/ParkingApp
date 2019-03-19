package nk.parking.owner;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Transactional
public interface OwnerService {
    Double totalAmountDaily(LocalDateTime from, LocalDateTime to);
}
