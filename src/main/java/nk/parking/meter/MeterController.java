package nk.parking.meter;

import nk.parking.customer.Customer;
import nk.parking.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/meter")
public class MeterController {

    private final MeterService meterService;
    private final CustomerService customerService;

    @GetMapping("/add")
    public List<Meter> addExampleMeters() {
        Meter meter = new Meter();
        Meter meter2 = new Meter();
        meter.setStartTime(LocalDateTime.now());
        meter2.setStartTime(LocalDateTime.now());
        Optional<Customer> customer = customerService.findById((long) 1);
        Optional<Customer> customer2 = customerService.findById((long) 2);
        meter.setCustomer(customer.orElse(null));
        meter2.setCustomer(customer2.orElse(null));
        meterService.save(meter);
        meterService.save(meter2);
        return meterService.findAll();
    }

    @GetMapping
    public List<Meter> loadAll() {
        return meterService.findAll();
    }

    @PostMapping(path = "/start")
    @ResponseStatus(HttpStatus.CREATED)
    public void startNewMeter(@RequestBody @Valid final NewMeterDTO newMeter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                builder.append(error.getDefaultMessage());
                builder.append(System.lineSeparator());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, builder.toString());
        } else {
            Optional<Customer> optCustomer = customerService.findById(newMeter.getCustomerId());
            if (!optCustomer.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer with such ID does not exist");
            } else {
                meterService.saveNewMeter(optCustomer.get(), newMeter.getStartTime());
            }
        }
    }

    @PostMapping(path = "/end")
    @ResponseStatus(HttpStatus.OK)
    public void endMeter(@RequestBody @Valid final MeterDTO meterToEnd, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                builder.append(error.getDefaultMessage());
                builder.append(System.lineSeparator());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, builder.toString());
        } else {
            meterService.endMeter(meterToEnd);
        }
    }

    @Autowired
    public MeterController(MeterService meterService, CustomerService customerService) {
        this.meterService = meterService;
        this.customerService = customerService;
    }
}
