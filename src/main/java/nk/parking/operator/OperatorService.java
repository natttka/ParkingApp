package nk.parking.operator;

import nk.parking.customer.Customer;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OperatorService {
    boolean isMeterRunning(Customer customer);
}
