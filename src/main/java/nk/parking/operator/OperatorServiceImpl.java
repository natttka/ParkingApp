package nk.parking.operator;

import nk.parking.customer.Customer;
import nk.parking.customer.CustomerRepository;
import nk.parking.meter.Meter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperatorServiceImpl implements OperatorService {

    private final CustomerRepository customerRepository;

    @Autowired
    public OperatorServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean isMeterRunning(Customer customer) {
        boolean isRunning = false;
        Customer cust = customerRepository.getOne(customer.getId());
        if (!cust.getMeters().isEmpty()) {
                for (Meter meter : cust.getMeters()) {
                    if (meter.getStartTime() != null && meter.getEndTime() == null) {
                        isRunning = true;
                    }
                }
        }
        return isRunning;
    }
}
