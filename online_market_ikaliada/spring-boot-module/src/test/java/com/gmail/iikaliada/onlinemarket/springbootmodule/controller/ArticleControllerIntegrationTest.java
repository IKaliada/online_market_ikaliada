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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerIntegrationTest {
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
    public void shouldReturnArticlesPageWithStatusOk() throws Exception {
        this.mockMvc.perform(get("/articles"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnArticlePageWhenRedirectToArticle() throws Exception {
        this.mockMvc.perform(post("/articles/article/delete"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnArticlesPageWhenDeleteCommentById() throws Exception {
        this.mockMvc.perform(post("/articles/article/deleteComment"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/articles/article"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnNewsPageWithStatusOk() throws Exception {
        this.mockMvc.perform(post("/articles/new/article"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }
}
