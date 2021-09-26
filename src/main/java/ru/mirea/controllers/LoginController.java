package ru.mirea.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mirea.services.EmailService;
import ru.mirea.services.EmailforAdmin;
import ru.mirea.сonfig.WebSecurityConfig;

@Controller
public class LoginController {
    private final EmailforAdmin emailService;

    @Autowired
    public LoginController(EmailforAdmin emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String checkAuth() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)

        ) {
            return "redirect:/admin";
        }

        //emailService.sendToAdmin(WebSecurityConfig.getNewPassword());
        return "login";
    }

 //   @GetMapping("/login?logoui")
   // public void logOut() {
     //   WebSecurityConfig config = new WebSecurityConfig();
     //   config.userDetailsService();
       // return "admin";
   // }
}
