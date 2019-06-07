package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ProfileService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForProfileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/public/users/profile")
    public String getProfileById(Model model) {
        UserForProfileDTO userForProfileDTO = profileService.getProfileById();
        model.addAttribute("user", userForProfileDTO);
        return "profile";
    }

    @PostMapping("/public/users/profile/update")
    public String updateProfile(@Valid @ModelAttribute("user") UserForProfileDTO userForProfileDTO,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }
        UserForProfileDTO user = profileService.updateProfile(userForProfileDTO);
        model.addAttribute("user", user);
        return "redirect:/public/users/profile";
    }
}
