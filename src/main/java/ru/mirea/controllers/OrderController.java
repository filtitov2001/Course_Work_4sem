package ru.mirea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.dto.OrderToCustomerRequest;
import ru.mirea.models.Order;
import ru.mirea.services.CustomerService;
import ru.mirea.services.FileUploadService;
import ru.mirea.services.OrderService;

import javax.naming.Binding;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @Value("${upload.path}")
    private String uploadPath;
    private String path = System.getProperty("user.dir").replace('\\', '/') + "/src/main/resources/";

    @PostMapping("/add")
    public RedirectView add(
            @Valid @ModelAttribute("addOrder") OrderToCustomerRequest orderToCustomerRequest,
            BindingResult result, @RequestParam("file") MultipartFile file){
        if(!result.hasErrors()){
            customerService.publish(orderToCustomerRequest);
            if (file != null && !file.getOriginalFilename().isEmpty()) {
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

    @PostMapping("{orderId}/delete")
    public RedirectView delete(@PathVariable long orderId) {
        orderService.delete(orderId);
        return new RedirectView("/admin");
    }


}
