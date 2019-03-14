package nk.parking.meter;

import nk.parking.customer.Customer;
import nk.parking.customer.CustomerRepository;
import nk.parking.fees.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeterServiceImpl implements MeterService {

    private final MeterRepository meterRepository;
    private final FeeService feeService;
    private final CustomerRepository customerRepository;

    @Override
    public Meter save(Meter meter) {
        return meterRepository.save(meter);
    }

    @Override
    public Optional<Meter> findById(Long id) {
        return meterRepository.findById(id);
    }

    @Override
    public List<Meter> findAll() {
        return meterRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        meterRepository.deleteById(id);
    }

    @Override
    public void saveNewMeter(Customer customer, LocalDateTime startTime) {
        Meter meter = new Meter();
        meter.setStartTime(startTime);
        customer.addMeter(meter);
        customerRepository.save(customer);
        meterRepository.save(meter);
    }

    @Override
    public void endMeter(MeterDTO meterToEnd) {
        Optional<Meter> optMeter = meterRepository.findById(meterToEnd.getId());
        if (optMeter.isPresent()) {
            Meter meter = optMeter.get();
            if (meter.getEndTime() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This meter already has end time");
            } else {
                meter.setEndTime(meterToEnd.getEndTime());
                meterRepository.save(meter);
                feeService.calculateFee(meter);
            }
        }
    }

    @Autowired
    public MeterServiceImpl(MeterRepository meterRepository, FeeService feeService, CustomerRepository customerRepository) {
        this.meterRepository = meterRepository;
        this.feeService = feeService;
        this.customerRepository = customerRepository;
    }
}
