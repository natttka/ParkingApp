package nk.parking.meter;

import nk.parking.customer.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
public interface MeterService {

    Meter save(Meter meter);

    Optional<Meter> findById(Long id);

    List<Meter> findAll();

    void deleteById(Long id);

    void saveNewMeter(Customer customer, LocalDateTime startTime);

    void endMeter(MeterDTO meterToEnd);
}
