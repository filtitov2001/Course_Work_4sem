package ru.mirea.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mirea.models.Customer;
import ru.mirea.models.Order;
import ru.mirea.repositories.CustomerRepository;
import ru.mirea.repositories.OrderRepository;
import ru.mirea.services.CustomerService;
import ru.mirea.services.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;

    @GetMapping
    public List<Customer> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    @PostMapping
    public List<Customer> add(
           // @Valid @ModelAttribute("addOrder") OrderToCustomerRequest orderToCustomerRequest
           @RequestBody Customer customer
            ){
        Customer customer1 = customerRepository.findByCustomerNameAndCustomerEmailAndCustomerNumber(
                customer.getCustomerName(),
                customer.getCustomerEmail(),
                customer.getCustomerNumber());
        if (customer1 != null) {
            for (Order order: customer.getOrders()) {
                order.setCustomer(customer1);
                orderRepository.save(order);
            }
        } else {
            customerRepository.save(customer);
            for (Order order: customer.getOrders()) {
                order.setCustomer(customer);
                orderRepository.save(order);
            }
        }

        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    @PutMapping("{customerId}")
    public List<Customer> update(@PathVariable long customerId,
                                 @RequestBody Customer customer) {

        customerRepository.findById(customerId)
                .map(customer1 -> {
                    if (customer.getCustomerName() != null) {
                        customer1.setCustomerName(customer.getCustomerName());
                    }
                    if (customer.getCustomerEmail() != null) {
                        customer1.setCustomerEmail(customer.getCustomerEmail());
                    }
                    if (customer.getCustomerNumber() != null) {
                        customer1.setCustomerNumber(customer.getCustomerNumber());
                    }
                    return customerRepository.save(customer1);
                }

        );

        List<Customer> customers = customerRepository.findAll();

        return customers;

    }


    @DeleteMapping("{customerId}")
    public Optional<Customer> delete(@PathVariable long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        customerService.delete(customerId);
        return customer;
    }
}
