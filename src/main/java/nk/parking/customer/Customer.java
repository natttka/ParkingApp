package nk.parking.customer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import nk.parking.currency.Currency;
import nk.parking.meter.Meter;
import nk.parking.operator.Operator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Customers")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "reg_number", nullable = false, unique = true)
    private String vehicleRegNo;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<Meter> meters = new ArrayList<>();

    @Column(name = "type", nullable = false)
    private CustomerType customerType;

    @Column(name = "currency", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    @JsonManagedReference
    private Operator operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public List<Meter> getMeters() {
        return meters;
    }

    public void setMeters(List<Meter> meters) {
        this.meters = meters;
    }

    public void addMeter(Meter meter) {
        this.meters.add(meter);
        meter.setCustomer(this);
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCustomerCurrency(Currency currency) {
        this.currency = currency;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
