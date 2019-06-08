package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

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

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.SELLER_AUTHORITY_CONSTANT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldGetItemsPageWhenGetItemAndConfirmStatusOk() throws Exception {
        this.mockMvc.perform(get("/private/items"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldGetItemsPageWhenDeleteItemsWhenIdsAreNull() throws Exception {
        this.mockMvc.perform(post("/private/items/delete"))
                .andDo(print())
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/private/items"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldGetItemsPageWhenCopyItemAndConfirmRedirectionStatus() throws Exception {
        this.mockMvc.perform(get("/private/items/1/copy"))
                .andDo(print())
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/private/items"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldGetItemsPageWhenDeleteItemAndConfirmRedirectionStatus() throws Exception {
        this.mockMvc.perform(post("/private/items/delete").param("ids", "2")
                .param("page", "1"))
                .andDo(print())
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/private/items"));
    }
}
