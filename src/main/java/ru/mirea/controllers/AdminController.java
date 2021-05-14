package ru.mirea.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.dto.DtoConverter;
import ru.mirea.services.CustomerService;
import ru.mirea.сonfig.WebSecurityConfig;

import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private DtoConverter dtoConverter;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String getCustomers(Map<String, Object> model) {
        model.put(
                "customers",
                customerService.takeAllCustomers(dtoConverter::toCustomerResponseList)
        );
        return "admin";
    }

    @PostMapping("{customerId}/delete")
    public RedirectView delete(@PathVariable long customerId) {
        customerService.delete(customerId);
        return new RedirectView("/admin");
    }

}
