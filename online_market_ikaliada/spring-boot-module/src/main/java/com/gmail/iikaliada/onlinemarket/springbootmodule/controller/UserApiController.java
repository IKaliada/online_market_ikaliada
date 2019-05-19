package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserApiController {

    private UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/private/users")
    public List<UserDTO> getUsers(@RequestParam(value = "page", defaultValue = "1") Integer pageSize) {
        return userService.getUsers(pageSize);
    }

    @PostMapping("/private/users")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        userService.add(userDTO);
        return new ResponseEntity<>(userDTO.toString(), HttpStatus.OK);
    }
}
