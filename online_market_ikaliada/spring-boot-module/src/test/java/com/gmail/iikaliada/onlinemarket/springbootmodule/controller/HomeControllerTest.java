package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Role;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;
    @MockBean
    private Role role;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldGetHomePage() throws Exception {
        this.mockMvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAboutPage() throws Exception {
        this.mockMvc.perform(get("/about"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAccessDeniedPage() throws Exception {
        this.mockMvc.perform(get("/403"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetLoginPage() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}
