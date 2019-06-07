package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.RoleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.RoleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.PaginationHandler;
import com.gmail.iikaliada.onlinemarket.springbootmodule.validation.UserValidation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.ADMIN_AUTHORITY_CONSTANT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private UserValidation userValidation;
    @Mock
    private RoleService roleService;
    @Mock
    private PaginationHandler pagination;

    @Before
    public void init() {
        UserController userController = new UserController(userService, userValidation, roleService, pagination);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageToUsers() throws Exception {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("name");
        userDTO.setMiddlename("middlename");
        userDTO.setLastname("lastname");
        userDTO.setEmail("email");
        userDTO.setPassword("1234");
        userDTO.setRole(roleDTO);
        roleDTO.setName(ADMIN_AUTHORITY_CONSTANT);
        List<UserDTO> users = Collections.singletonList(userDTO);
        int pageSize = 1;
        when(userService.getUsers(pageSize)).thenReturn(users);
        this.mockMvc.perform(get("/private/users.html").requestAttr("page", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", users))
                .andExpect(forwardedUrl("users"));
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageToUsersWhenUpdateUserPassword() throws Exception {
        this.mockMvc.perform(post("/private/users/1/password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/private/users/forward"));
    }
}
