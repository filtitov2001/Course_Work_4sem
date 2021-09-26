package ru.mirea.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mirea.dto.OrderToCustomerRequest;
import ru.mirea.models.Customer;
import ru.mirea.models.Order;
import ru.mirea.repositories.CustomerRepository;
import ru.mirea.repositories.OrderRepository;

import java.io.File;
import java.util.Optional;

@Service
public class OrderService {
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
    public OrderService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void publish(OrderToCustomerRequest request, String filePath){
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
        order.setImage(filePath);
        order.setCustomer(customer);
        orderRepository.save(order);
      //  if(emailService!=null)
        //    emailService.send("Поступил новый заказ", request.getCustomerEmail());
    }

    public void delete(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            String truePath = path + uploadPath + "/images/";
            File file = new File(truePath + order.get().getImage());
            if(!order.get().getImage().equals("null.png")){
                if(file.delete()){
                    orderRepository.deleteById(id);
                    System.out.println("Файл" + truePath + order.get().getImage() + " удален");
                }
                else
                    System.out.println("Файл" + truePath + order.get().getImage() + "Не удален");
            }
            else {
                orderRepository.deleteById(id);
            }
        }

    }
}
