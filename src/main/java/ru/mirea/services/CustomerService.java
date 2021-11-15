package ru.mirea.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mirea.dto.OrderToCustomerRequest;
import ru.mirea.models.Customer;
import ru.mirea.models.Order;
import ru.mirea.repositories.CustomerRepository;
import ru.mirea.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
public class CustomerService {
    @Value("${upload.path}")
    private String uploadPath;

    String path = System.getProperty("user.dir")
            .replace('\\', '/') + "/src/main/resources/";
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    private final EmailService emailService;

    @Autowired
    public CustomerService(EmailService emailService) {
        this.emailService = emailService;
    }
//    public void save(Customer customer){
//        customer = customerRepository.
//                findByCustomerNameAndCustomerEmailAndCustomerNumber(
//                        customer.getCustomerName(),
//                        customer.getCustomerEmail(),
//                        customer.getCustomerNumber());
//        if(customer != null){
//            return;
//        }
//        customer = new Customer();
//        customer.setCustomerName(request.getCustomerName());
//        customer.setCustomerEmail(request.getCustomerEmail());
//        customer.setCustomerNumber(request.getCustomerNumber());
//        customerRepository.save(customer);
//    }

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
        List<Order> orders = orderRepository.findAllById(Collections.singleton(id));
        String truePath = path + uploadPath + "/images/";
        for(Order order: orders) {
            File file = new File(truePath + order.getImage());
            if(!order.getImage().equals("null.png")){
                if(file.delete()){
                    orderRepository.deleteById(id);
                    System.out.println("Файл" + truePath + order.getImage() + " удален");
                }
                else
                    System.out.println("Файл" + truePath + order.getImage() + "Не удален");
            }
        }
        orderRepository.deleteAllByCustomer(customer.get());

        customerRepository.deleteById(id);
    }

    @Transactional
    public void update(long id) {
        Optional<Customer> customer = customerRepository.findById(id);


    }

}
