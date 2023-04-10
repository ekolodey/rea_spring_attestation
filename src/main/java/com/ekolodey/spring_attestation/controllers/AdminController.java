package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.models.Category;
import com.ekolodey.spring_attestation.models.Image;
import com.ekolodey.spring_attestation.models.Product;
import com.ekolodey.spring_attestation.repositories.CategoryRepository;
import com.ekolodey.spring_attestation.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    public AdminController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("admin/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/add";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one")MultipartFile file_one, @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three, @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five")MultipartFile file_five, @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/add";
        }

        if(!file_one.isEmpty()){
            uploadImage(product, file_one);
        }

        if(!file_two.isEmpty()){
            uploadImage(product, file_two);
        }

        if(!file_three.isEmpty()){
            uploadImage(product, file_three);
        }

        if(!file_four.isEmpty()){
            uploadImage(product, file_four);
        }

        if(!file_five.isEmpty()){
            uploadImage(product, file_five);
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }

    private void uploadImage(Product product, MultipartFile file_one) throws IOException {
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
        file_one.transferTo(new File(uploadDir + "/" + resultFileName).getAbsoluteFile());
        Image image = new Image();
        image.setProduct(product);
        image.setFileName(resultFileName);
        product.addImageToProduct(image);
    }


    @GetMapping("/admin")
    public String admin(Model model)
    {
        model.addAttribute("products", productService.getAllProduct());
        return "admin";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/edit";


    }

    @PostMapping("admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/edit";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

}
