package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;


import com.gmail.iikaliada.onlinemarket.servicemodule.RoleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.RoleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.validation.UserValidation;
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
import java.util.stream.Collectors;


@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${failed.update.message}")
    String emailError;
    @Value("${user.exists.message}")
    String emailInsertError;
    @Value("${not.allowed.message}")
    String notAllowedMessage;

    private final UserService userService;
    private final UserValidation userValidation;
    private final RoleService roleService;

    public UserController(UserService userService, UserValidation userValidation, RoleService roleService) {
        this.userService = userService;
        this.userValidation = userValidation;
        this.roleService = roleService;
    }

    @GetMapping("/private/users")
    public String getUsers(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        model.addAttribute("currentPage", currentPage);
        List<UserDTO> users = userService.getUsers(currentPage);
        model.addAttribute("users", users);
        getRoleForController(model);
        UserDTO user = new UserDTO();
        user.setRole(new RoleDTO());
        model.addAttribute("user", user);
        int totalPage = userService.getTotalPages();
        model.addAttribute("totalPage", totalPage);
        if (currentPage > 1) {
            int previousPage = currentPage - 1;
            model.addAttribute("previousPage", previousPage);
        }
        if (currentPage < totalPage) {
            int nextPage = currentPage + 1;
            model.addAttribute("nextPage", nextPage);
        }
        int[] pages = new int[totalPage];
        for (int i = 0; i < totalPage; i++) {
            pages[i] = i + 1;
        }
        model.addAttribute("pages", pages);
        return "users";
    }

    @PostMapping("/private/users/delete")
    public String deleteUserById(@Nullable @RequestParam("ids") Long[] ids, Model model,
                                 @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        if (ids == null) {
            return "redirect:/private/users";
        }
        List<Long> collect = Arrays.stream(ids).collect(Collectors.toList());
        try {
            userService.deleteUserById(collect);
        } catch (Exception e) {
            getUsers(model, currentPage);
            model.addAttribute("notAllowedMessage", notAllowedMessage);
            return "users";
        }
        return "redirect:/private/users";
    }

    @PostMapping("/private/users/{id}/update")
    public String updateUsersAuthority(@PathVariable("id") Long id, UserDTO user,  BindingResult result, Model model,
                                       @RequestParam(name = "page", defaultValue = "1") Integer currentPage) {
        model.addAttribute("user", user);
        userValidation.validate(id, result);
        if (result.hasErrors()) {
            getUsers(model, currentPage);
            model.addAttribute("emailError", emailError);
            return "users";
        }
        userService.updateUsersRole(id, user.getRole().getId());
        return "redirect:/private/users";
    }

    @GetMapping("/private/users/add_user")
    public String addUserPage(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        getRoleForController(model);
        return "add_user";
    }

    @PostMapping("/private/users/add_user")
    public String addUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            getRoleForController(model);
            return "add_user";
        }
        try {
            userService.add(userDTO);
        } catch (Exception e) {
            getRoleForController(model);
            model.addAttribute("emailInsertError", emailInsertError);
            return "add_user";
        }
        return "redirect:/private/users";
    }

    @PostMapping("/private/users/{email}/password")
    public String changePassword(@PathVariable("email") String email) {
        userService.updateUserPassword(email);
        return "redirect:/password_success_message";
    }

    @GetMapping("/password_success_message")
    public String getSuccessUpdatedPassword() {
        return "password_success_message";
    }

    private void getRoleForController(Model model) {
        List<RoleDTO> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
    }
}
