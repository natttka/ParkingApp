package nk.parking.customer;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CustomerService {

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    void deleteById(Long id);
}
