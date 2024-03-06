package org.example.admin.controller;

import org.example.library.dto.ProductDto;
import org.example.library.model.Category;
import org.example.library.model.Product;
import org.example.library.service.CategoryService;
import org.example.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
//    @GetMapping("/products")
//    public String products(Model model) {
//        List<ProductDto> productDtoList = productService.findAll();
//        model.addAttribute("products", productDtoList);
//        model.addAttribute("size", productDtoList.size());
//        return "products";
//    }

    @GetMapping("/products")
    public String products(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        model.addAttribute("title", "Produkty");
        return "products";
    }

    @GetMapping("/addProduct")
    public String addProductForm(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "addProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") ProductDto productDto,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes attributes){
        try{
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Dodano pomyślnie!");
        } catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Nie udało się dodać!");
        }
        return "redirect:/products";
    }

    @GetMapping("/updateProduct/{id}")
    public String updateProductForm(@PathVariable("id") Long id,
                                    Model model,
                                    Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        ProductDto productDto = productService.getById(id);

        model.addAttribute("title", "Edycja produktu");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "updateProduct";
    }

//    @PostMapping("/updateProduct/{id}")
//    public String updateProduct(@PathVariable("id") Long id,
//                                @ModelAttribute("productDto") ProductDto productDto,
//                                @RequestParam("imageProduct") MultipartFile imageProduct,
//                                RedirectAttributes attributes){
//        try {
//            productService.update(imageProduct, productDto);
//            attributes.addFlashAttribute("success", "Pomyślnie zaktualizowano produkt!");
//        }catch (Exception e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("error", "Nie udało się zaktualizować produktu!");
//        }
//        return "redirect:/products";
//    }
@PostMapping("/updateProduct/{id}")
public String updateProduct(@PathVariable("id") Long id,
                            @ModelAttribute("productDto") ProductDto productDto,
                            @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                            RedirectAttributes attributes) {
    try {
        // Sprawdzamy, czy użytkownik wybrał nowe zdjęcie
        if (imageProduct != null && !imageProduct.isEmpty()) {
            // Jeśli wybrano nowe zdjęcie, zapisujemy je do bazy danych i aktualizujemy produkt
            productService.update(imageProduct, productDto);
        } else {
            // Jeśli nie wybrano nowego zdjęcia, używamy istniejącego zdjęcia produktu z obiektu ProductDto
            productService.updateWithoutImage(productDto);
        }
        attributes.addFlashAttribute("success", "Pomyślnie zaktualizowano produkt!");
    } catch (Exception e) {
        e.printStackTrace();
        attributes.addFlashAttribute("error", "Nie udało się zaktualizować produktu!");
    }
    return "redirect:/products";
}

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        }
        return "redirect:/products";
    }


}

