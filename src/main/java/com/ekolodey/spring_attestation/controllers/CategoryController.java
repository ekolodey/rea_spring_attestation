package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class CategoryController {

    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("categories")
    public String list(Model model){
        model.addAttribute("list", repository.findAll());
        return "category/list";
    }
}
