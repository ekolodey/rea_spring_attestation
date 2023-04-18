package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.models.Person;
import com.ekolodey.spring_attestation.repositories.PersonRepository;
import com.ekolodey.spring_attestation.services.PersonService;
import com.ekolodey.spring_attestation.util.PersonValidator;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final PersonValidator personValidator;
    private final PersonService personService;
    private final PersonRepository repository;

    public UserController(PersonValidator personValidator, PersonService personService, PersonRepository repository) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.repository = repository;
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

    @GetMapping("/users")
    public String list(Model model){
        model.addAttribute("users", repository.findAll());
        return "user/list";
    }

    @GetMapping("/user/{id}")
    public String info(@PathVariable("id") Integer userId, Model model){
        model.addAttribute("user", repository.findById(userId).get());
        return "user/info";
    }

    @PostMapping("/user/{id}")
    public String update(@PathVariable("id") Integer userId, @ModelAttribute("role") String role, Model model){
        Person person = repository.findById(userId).get();
        person.setRole(role);
        repository.save(person);
        model.addAttribute("user", person);
        return "redirect:/users";
    }

}
