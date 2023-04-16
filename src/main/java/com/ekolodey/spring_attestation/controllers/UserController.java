package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.models.Person;
import com.ekolodey.spring_attestation.models.Product;
import com.ekolodey.spring_attestation.repositories.CategoryRepository;
import com.ekolodey.spring_attestation.services.PersonService;
import com.ekolodey.spring_attestation.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PersonValidator personValidator;
    private final PersonService personService;

    public UserController(PersonValidator personValidator, PersonService personService) {
        this.personValidator = personValidator;
        this.personService = personService;
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }
    @GetMapping("/register")
    public String registration(@ModelAttribute("person") Person person){
        return "user/register";
    }

    @PostMapping("/register")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "user/register";
        }
        personService.register(person);
        return "redirect:/";
    }

}
