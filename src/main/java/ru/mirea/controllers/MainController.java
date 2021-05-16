package ru.mirea.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {
    @GetMapping("/")
    public String getMain() {
        return "home";
    }
    @GetMapping("/contacts")
    public String getContacts() {
        return "contacts/contacts";
    }
    @GetMapping("/delivery")
    public String getDelivery() {
        return "delivery/delivery";
    }
    @GetMapping("/how-do-it")
    public String getHDI() {
        return "HDI/HDI";
    }
    @GetMapping("/create")
    public String getCreate() {
        return "create";
    }
    @GetMapping("/gallery")
    public String getGallery() {
        return "gallery/gallery";
    }


}

