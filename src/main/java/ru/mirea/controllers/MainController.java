package ru.mirea.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
//    @GetMapping("/")
//    public String getMain() {
//        return "static/home";
//    }
//    @GetMapping("/contacts")
 //   public String getContacts() {
 //       return "static/contacts/contacts";
 //   }
//    @GetMapping("/delivery")
//    public String getDelivery() {
//        return "static/delivery/delivery";
//    }
 //   @GetMapping("/how-do-it")
//    public String getHDI() {
//        return "static/HDI/HDI";
//    }
    @GetMapping("/create")
    public String getCreate() {
        return "static/create/create";
    }
//    @GetMapping("/gallery")
//    public String getGallery() {
//        return "static/gallery/gallery";
//    }


}

