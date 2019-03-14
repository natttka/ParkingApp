package nk.parking.customer;

import nk.parking.currency.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> findAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping(path = "/add")
    public List<Customer> addExampleCustomers() {
        Customer customer1 = new Customer();
        customer1.setCustomerCurrency(Currency.PLN);
        customer1.setCustomerType(CustomerType.REGULAR);
        customer1.setVehicleRegNo("ABCD997");
        Customer customer2 = new Customer();
        customer2.setCustomerCurrency(Currency.PLN);
        customer2.setCustomerType(CustomerType.REGULAR);
        customer2.setVehicleRegNo("ABCD998");
        customerService.save(customer1);
        customerService.save(customer2);
        return customerService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Customer findCustomerById(@PathVariable(name = "id") final String id) {
        try{
            Optional<Customer> optCustomer = customerService.findById(Long.parseLong(id));
            if (optCustomer.isPresent()) {
                return optCustomer.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
            }
        } catch (NumberFormatException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCustomer(@RequestBody @Valid final Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                builder.append(error.getDefaultMessage());
                builder.append(System.lineSeparator());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, builder.toString());
        } else {
            customerService.save(customer);
        }
    }

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
}
