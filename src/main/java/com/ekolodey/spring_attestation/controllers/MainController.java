package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.security.PersonDetails;
import com.ekolodey.spring_attestation.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof PersonDetails){
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            String role = personDetails.getPerson().getRole();

            if (Objects.equals(role, "ROLE_ADMIN"))
                return "admin/index";
            else
                return "user/index";
        }
        else {
            return "redirect:/products";
        }
    }

}
