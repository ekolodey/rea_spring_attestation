package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.models.Product;
import com.ekolodey.spring_attestation.search.ProductSearchParam;
import com.ekolodey.spring_attestation.repositories.CategoryRepository;
import com.ekolodey.spring_attestation.repositories.ProductRepository;
import com.ekolodey.spring_attestation.search.ProductSearchSpecification;
import com.ekolodey.spring_attestation.services.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private  final ProductService productService;
    private final CategoryRepository categoryRepository;


    public ProductController(ProductRepository productRepository, ProductService productService, CategoryRepository categoryRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping("/product/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "/product/info";
    }

    @GetMapping("/products")
    public String list(Model model){
        model.addAttribute("all_products", productService.getAllProduct());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("search", new ProductSearchParam());

        return "product/list";
    }

    @PostMapping("/products")
    public String productSearch(@ModelAttribute("search") ProductSearchParam search, Model model){
        model.addAttribute("categories", categoryRepository.findAll());

        ProductSearchSpecification specification = new ProductSearchSpecification(search);
        model.addAttribute("search_result", productRepository.findAll(specification));

        return "/product/list";

    }
}
