package com.example.arun.springguru.springmvc.controllers;

import com.example.arun.springguru.springmvc.domain.Product;
import com.example.arun.springguru.springmvc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {


    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/product/list")
    public String listProducts(Model model){

        model.addAttribute("products", productService.listAll());
        return "product/list";
    }

    @GetMapping("/product/show/{id}")
    public String getProduct(@PathVariable Integer id, Model model){
        model.addAttribute("product", productService.getById(id));
        return "product/show";
    }

    @PostMapping("/product")
    public String saveOrUpdateProduct(Product product){
        Product savedProduct = productService.saveOrUpdate(product);
        return "redirect:/product/show/" + savedProduct.getId();
    }

    // Method just to show the CRUD Form
    @GetMapping("/product/new")
    public String newProduct(Model model){
        // passing empty object, so that we can use it for
        // both update & insert flows to accept new values.
        model.addAttribute("product",new Product());
        return "product/productform";
    }

    @GetMapping("/product/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("product", productService.getById(id));
        return "product/productform";
    }

    @GetMapping("/product/delete/{id}")
    public String delete(@PathVariable Integer id){
        productService.delete(id);
        return "redirect:/product/list";
    }
}
