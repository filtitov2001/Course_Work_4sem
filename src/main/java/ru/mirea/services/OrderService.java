package ru.mirea.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.dto.OrderToCustomerRequest;
import ru.mirea.models.Customer;
import ru.mirea.models.Order;
import ru.mirea.repositories.CustomerRepository;
import ru.mirea.repositories.OrderRepository;

@Service
public class OrderService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    private final EmailService emailService;

    @Autowired
    public OrderService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void publish(OrderToCustomerRequest request){
        String customerName = request.getCustomerName();
        String customerEmail = request.getCustomerEmail();
        String customerNumber = request.getCustomerNumber();
        Customer customer = customerRepository.
                findByCustomerNameAndCustomerEmailAndCustomerNumber(customerName,customerEmail,customerNumber);
        if(customer == null){
            return;
        }
        Order order = new Order();
        order.setCakeName(request.getOrderCakeName());
        order.setCreamName(request.getOrderCreamName());
        order.setFillerName(request.getOrderFillerName());
        order.setDate(request.getOrderDate());
        order.setComment(request.getOrderComment());
        order.setCustomer(customer);
        orderRepository.save(order);
        if(emailService!=null)
            emailService.send("Поступил новый заказ", request.getCustomerEmail());
    }

    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
