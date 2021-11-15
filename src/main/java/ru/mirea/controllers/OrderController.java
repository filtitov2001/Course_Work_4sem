package ru.mirea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.dto.OrderResponse;
import ru.mirea.dto.OrderToCustomerRequest;
import ru.mirea.models.Customer;
import ru.mirea.models.Order;
import ru.mirea.repositories.OrderRepository;
import ru.mirea.services.CustomerService;
import ru.mirea.services.FileUploadService;
import ru.mirea.services.OrderService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @Value("${upload.path}")
    private String uploadPath;
    private String path = System.getProperty("user.dir").replace('\\', '/') + "/src/main/resources/";

    @GetMapping
    public List<Order> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return  orders;
    }


    @PostMapping("/add")
    public RedirectView add(
            @Valid @ModelAttribute("addOrder") OrderToCustomerRequest orderToCustomerRequest,
            BindingResult result, @RequestParam(value = "file") MultipartFile file){
        if(!result.hasErrors()){
            customerService.publish(orderToCustomerRequest);
            if (!file.isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + '.' + file.getOriginalFilename();
                System.out.println(path + uploadPath + "/images/" + resultFilename);
                try {
                    file.transferTo(new File(path + uploadPath + "/images/" + resultFilename));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                orderService.publish(orderToCustomerRequest,resultFilename);
            }
            else{
                orderService.publish(orderToCustomerRequest,"null.png");
            }

        }
        return new RedirectView("/create");
    }

    @PutMapping("{orderId}")
    public RedirectView update(@PathVariable long orderId,
                           @RequestBody Order order) {
        orderRepository.findById(orderId)
                .map(order1 -> {
                    if (order.getCakeName() != null) {
                        order1.setCakeName(order.getCakeName());
                    }
                    if (order.getCreamName() != null) {
                        order1.setCreamName(order.getCreamName());
                    }
                    if (order.getFillerName() != null) {
                        order1.setFillerName(order.getFillerName());
                    }
                    if (order.getFillerName() != null) {
                        order1.setFillerName(order.getFillerName());
                    }
                    if (order.getDate() != null) {
                        order1.setDate(order.getDate());
                    }
                    if (order.getComment() != null) {
                        order1.setComment(order.getComment());
                    }
                    if (order.getImage() != null) {
                        order1.setImage(order.getImage());
                    }
                    return orderRepository.save(order1);
                });
        return new RedirectView("/order");

    }

    @DeleteMapping("{orderId}")
    public List<Order> deleteOrder(@PathVariable long orderId) {
        orderService.delete(orderId);
        return orderRepository.findAll();
    }


    @DeleteMapping("{orderId}/delete")
    public RedirectView delete(@PathVariable long orderId) {
        orderService.delete(orderId);
        return new RedirectView("/admin");
    }


}
