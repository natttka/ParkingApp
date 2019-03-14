package nk.parking.fees;

import nk.parking.meter.Meter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface FeeService {

    Fee save(Fee fee);

    Optional<Fee> findById(Long id);

    void deleteById(Long id);

    void calculateFee(Meter meter);
}
