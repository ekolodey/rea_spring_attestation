package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.models.Product;
import com.ekolodey.spring_attestation.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {


    @GetMapping("/authentication")
    public String login(){
        return "authentication";
    }

}
