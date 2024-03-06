package org.example.admin.controller;

import org.example.library.model.Category;
import org.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
            attributes.addFlashAttribute("success", "Dodano kategorię!");
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Nie udało się dodać kategorii! Kategoria już istnieje w bazie danych!");
        } catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Błąd serwera!");
        }
        return "redirect:/categories";
    }
    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id){
        return categoryService.findById(id);
    }
    @GetMapping("/updateCategory")
    public String update(Category category, RedirectAttributes redirectAttributes) {
        try {
            categoryService.update(category);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/categories";
    }
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            categoryService.deleteCategory(id);
            attributes.addFlashAttribute("success", "Kategoria została pomyślnie usunięta.");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Nie można usunąć kategorii, ponieważ jest powiązana z produktami.")) {
                attributes.addFlashAttribute("failed", "Usunięcie kategorii jest niemożliwe ze względu na istniejące powiązania z produktami. " +
                        "Należy najpierw dokonać zmiany kategorii dla tych produktów, a dopiero potem ponowić próbę usunięcia kategorii.");
            } else {
                attributes.addFlashAttribute("failed", "Wystąpił błąd podczas usuwania kategorii.");
            }
        }
        return "redirect:/categories";
    }

}
