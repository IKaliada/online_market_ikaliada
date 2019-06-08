package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.RoleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.ADMIN_AUTHORITY_CONSTANT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private UserDTO userDTO = new UserDTO();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        userDTO.setName("name");
        userDTO.setMiddlename("middlename");
        userDTO.setLastname("lastname");
        userDTO.setEmail("email@email.com");
        userDTO.setRole(roleDTO);
        roleDTO.setName(ADMIN_AUTHORITY_CONSTANT);
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldGetUsersPage() throws Exception {
        this.mockMvc.perform(get("/private/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldGetAddUsersPage() throws Exception {
        this.mockMvc.perform(get("/private/users/add_user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageWhenDeleteUsersWhenIdsNull() throws Exception {
        this.mockMvc.perform(post("/private/users/delete"))
                .andDo(print())
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/private/users"));
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageWhenDeleteUsersWithNotAllowedDeleteRole() throws Exception {
        this.mockMvc.perform(post("/private/users/delete").param("ids", "5")
                .param("page", "1"))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageWhenDeleteUsers() throws Exception {
        this.mockMvc.perform(post("/private/users/delete").param("ids", "6")
                .param("page", "1"))
                .andDo(print())
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/private/users"));
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageWhenAddUser() throws Exception {
        userDTO.setEmail("newEmail@email.com");
        this.mockMvc.perform(post("/private/users/add_user").flashAttr("userDTO", userDTO))
                .andDo(print())
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/private/users"));
    }

    @Test(expected = AssertionError.class)
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageWhenAddUserIfMethodReturnException() throws Exception {
        this.mockMvc.perform(post("/private/users/add_user").flashAttr("userDTO", userDTO))
                .andDo(print())
                .andExpect(status()
                        .isExpectationFailed())
                .andExpect(redirectedUrl("/private/users/add_user"));
    }

    @Test(expected = AssertionError.class)
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnAddUserPageWhenAddUserIfNotCorrectFilledTheFields() throws Exception {
        userDTO.setName("");
        this.mockMvc.perform(post("/private/users/add_user").flashAttr("userDTO", userDTO))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(forwardedUrl("add_user"));
    }
}
