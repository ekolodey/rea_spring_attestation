package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.models.Category;
import com.ekolodey.spring_attestation.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

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

    @GetMapping("category/info/{id}")
    public String list(@PathVariable("id") int id, Model model){
        repository.findById(id).ifPresent(category -> model.addAttribute("category", category));
        return "category/info";
    }


    @GetMapping("category/add")
    public String add(Model model){
        model.addAttribute("category", new Category());
        return "category/edit";
    }

    @PostMapping("category/edit")
    public String postEdit(@ModelAttribute("category") Category category, Model model){
        repository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("category/delete/{id}")
    public String delete(@PathVariable("id") int id){
        repository.deleteById(id);
        return "redirect:/categories";
    }
}
