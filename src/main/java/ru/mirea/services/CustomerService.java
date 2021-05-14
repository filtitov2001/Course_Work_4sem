package ru.mirea.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.dto.OrderToCustomerRequest;
import ru.mirea.models.Customer;
import ru.mirea.repositories.CustomerRepository;
import ru.mirea.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    private final EmailService emailService;

    @Autowired
    public CustomerService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void publish(OrderToCustomerRequest request){
        String customerName = request.getCustomerName();
        String customerEmail = request.getCustomerEmail();
        String customerNumber = request.getCustomerNumber();
        Customer customer = customerRepository.
                findByCustomerNameAndCustomerEmailAndCustomerNumber(customerName,customerEmail,customerNumber);
        if(customer != null){
            return;
        }
        customer = new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setCustomerNumber(request.getCustomerNumber());
        customerRepository.save(customer);
    }

    @Transactional
    public <T> T takeAllCustomers(Function<List<Customer>, T> toDto) {
        List<Customer> customers = customerRepository.findAll();
        return toDto.apply(customers);
    }

    @Transactional
    public <T> T takeCustomerById(long id, Function<Customer, T> toDto){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            return null;
        }
        return toDto.apply(customer.get());
    }

    @Transactional
    public void delete(long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return;
        }
        orderRepository.deleteAllByCustomer(customer.get());
        customerRepository.deleteById(id);
    }

}
