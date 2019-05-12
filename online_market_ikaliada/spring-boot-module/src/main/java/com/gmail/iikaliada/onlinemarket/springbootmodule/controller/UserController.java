package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;


import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Value("${not.allowed.message}")
    private String message;
    @Value("${user.is.exists.message}")
    private String USER_EXISTS;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/private/users")
    public String getUsers(
            Model model,
            @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        model.addAttribute("currentPage", currentPage);
        List<UserDTO> users = userService.getUsers(currentPage);
        int amountPage = userService.getTotalPages();
        model.addAttribute("users", users);
        model.addAttribute("amountPage", amountPage);
        if (currentPage > 1) {
            int previousPage = currentPage - 1;
            model.addAttribute("previousPage", previousPage);
        }
        if (currentPage < amountPage) {
            int nextPage = currentPage + 1;
            model.addAttribute("nextPage", nextPage);
        }
        int[] pages = new int[amountPage];
        for (int i = 0; i < amountPage; i++) {
            pages[i] = i + 1;
        }
        model.addAttribute("pages", pages);
        return "users";
    }

    @PostMapping("/private/users/delete")
    public String deleteUserById(@Nullable @RequestParam("ids") Long[] ids) {
        if (ids == null) {
            return "redirect:/private/users";
        }
        List<Long> collect = Arrays.stream(ids).collect(Collectors.toList());
        userService.deleteUserById(collect);
        return "redirect:/private/users";
    }

    @PostMapping("/private/users/update/{id}")
    public String updateUsersAuthority(@PathVariable("id") Long id, @RequestParam("role") String roleName, Model model) {
        String updateUsersRole = userService.updateUsersRole(id, roleName);
        if (updateUsersRole != null) {
            model.addAttribute("userMessage", message);
            return "users";
        }
        return "redirect:/private/users";
    }

    @GetMapping("/private/users/add_user")
    public String addUserPage(@ModelAttribute UserDTO userDTO) {
        return "add_user";
    }

    @PostMapping("/private/users/add_user")
    public String addUser(@Valid @ModelAttribute UserDTO userDTO,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add_user";
        }
        userService.add(userDTO);
        return "redirect:/private/users";
    }

    @PostMapping("/private/users/password/{email}")
    public String changePassword(@PathVariable("email") String email) {
        userService.updateUserPassword(email);
        return "redirect:/password_success_message";
    }

    @GetMapping("/password_success_message")
    public String getSuccessUpdatedPassword() {
        return "password_success_message";
    }
}
