package org.example.customer.controller;

import org.example.library.dto.ProductDto;
import org.example.library.model.Category;
import org.example.library.service.CategoryService;
import org.example.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model){
        return "home";
    }
//
    @GetMapping("/home")
    public String index(Model model){
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtoList);
        return "index";
    }
}
