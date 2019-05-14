package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/403")
    public String getAccessDeniedPage() {
        return "denied";
    }

    @GetMapping("/login")
    public String loginUser(LoginDTO loginDTO) {
        return "login";
    }

    @PostMapping("/login")
    public String loginUserToSite(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        model.addAttribute("loginDTO", loginDTO);
        return "redirect:/home";
    }
}
