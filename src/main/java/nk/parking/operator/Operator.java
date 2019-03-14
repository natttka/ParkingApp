package nk.parking.operator;

import nk.parking.customer.Customer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "customer")
    private List<Customer> customers = new ArrayList<>();

    public Operator() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
