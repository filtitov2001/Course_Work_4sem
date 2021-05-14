package ru.mirea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.dto.OrderToCustomerRequest;
import ru.mirea.services.CustomerService;
import ru.mirea.services.OrderService;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @PostMapping("/add")
    public RedirectView add(
            @Valid @ModelAttribute("addOrder") OrderToCustomerRequest orderToCustomerRequest,
            BindingResult result){
        if(!result.hasErrors()){
            customerService.publish(orderToCustomerRequest);
            orderService.publish(orderToCustomerRequest);
        }
        return new RedirectView("/create");
    }

    @PostMapping("{orderId}/delete")
    public RedirectView delete(@PathVariable long orderId) {
        orderService.delete(orderId);
        return new RedirectView("/admin");
    }


}
