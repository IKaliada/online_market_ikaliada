package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ProfileService;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForProfileDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("/public/users/profile")
    public String getProfileById(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        LoginDTO user = userService.getUsersByUsername(currentPrincipalName);
        UserForProfileDTO userForProfileDTO = profileService.getProfileById(user.getId());
        model.addAttribute("user", userForProfileDTO);
        return "profile";
    }

    @PostMapping("/public/users/{id}/profile/update")
    public String updateProfile(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("user") UserForProfileDTO userForProfileDTO,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }
        UserForProfileDTO user = profileService.updateProfile(userForProfileDTO);
        model.addAttribute("user", user);
        return "redirect:/public/users/profile";
    }
}
