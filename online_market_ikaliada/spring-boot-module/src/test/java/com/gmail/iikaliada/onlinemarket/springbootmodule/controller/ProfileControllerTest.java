package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ProfileService;
import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ProfileDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.ADMIN_AUTHORITY_CONSTANT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ProfileService profileService;
    @Mock
    private UserService userService;

    @Before
    public void init() {
        ProfileController profileController = new ProfileController(profileService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldReturnProfilePage() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(1L);
        profileDTO.setTelephone("123456");
        profileDTO.setAddress("Belarus");
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name");
        userDTO.setLastname("lastname");
        userDTO.setPassword("123456");
        profileDTO.setUserDTO(userDTO);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("email");
        when(userService.getUsersByUsername(userDTO.getEmail())).thenReturn(loginDTO);
        when(profileService.getProfileById(loginDTO.getId())).thenReturn(profileDTO);
        this.mockMvc.perform(get("/public/users/profile.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("profile", profileDTO))
                .andExpect(forwardedUrl("profile"));
    }
}
