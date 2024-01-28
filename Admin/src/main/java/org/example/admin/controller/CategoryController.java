package org.example.admin.controller;

import org.example.library.model.Category;
import org.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Kategorie");
        model.addAttribute("newCategory", new Category());
        return "categories";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("newCategory") Category category, RedirectAttributes attributes){
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Dodano kategorie!");
        } catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Nie udało się dadać kategorii!");

        }
        return "redirect:/categories";
    }


}
