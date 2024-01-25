package org.example.admin.controller;

import org.example.library.dto.AdminDto;
import org.example.library.model.Admin;
import org.example.library.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;

    private final BCryptPasswordEncoder passwordEncoder;


    @RequestMapping("/login")
    public String loginForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/index";
        }
        model.addAttribute("title", "Strona logowania");
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", "Strona główna");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/index";
        }
        model.addAttribute("title", "Strona rejestracji");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model) {

        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("emailError", "Podany adres e-mail jest już zarejestrowany!");
                System.out.println("admin not null");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("success", "Rejestracja przebiegła pomyślnie. Możesz teraz zalogować się na swoje konto!");
                model.addAttribute("adminDto", adminDto);

            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Nieprawidłowe hasło! Spróbuj poownie!");
                System.out.println("Hasło nie jest takie samo!");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "Wystąpił błąd serwera!");
        }
        return "register";

    }
}