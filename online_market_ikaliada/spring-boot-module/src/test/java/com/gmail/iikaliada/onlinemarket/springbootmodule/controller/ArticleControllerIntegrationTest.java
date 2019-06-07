package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForNewsDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.CommentDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForUiDTO;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;

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
        this.mockMvc.perform(post("/articles/article/delete").param("ids", "3")
                .param("page", "1"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnArticlePageWhenRedirectToArticleWhenIdIsNull() throws Exception {
        this.mockMvc.perform(post("/articles/article/delete"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnArticlesPageWhenDeleteCommentById() throws Exception {
        this.mockMvc.perform(post("/articles/article/deleteComment").param("ids", "2"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnArticlePageWhenDeleteCommentByIdWhenIdIsNull() throws Exception {
        this.mockMvc.perform(post("/articles/article/deleteComment"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("/articles/article"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnNewsPageWithStatusOkWhenArticlePageHasErrors() throws Exception {
        this.mockMvc.perform(post("/articles/new/article"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnNewsPageWithStatusOkWhen() throws Exception {
        this.mockMvc.perform(get("/articles/new/article"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldRedirectToArticlePageWhenSaveNewArticle() throws Exception {
        ArticleForNewsDTO articleForNewsDTO = new ArticleForNewsDTO();
        articleForNewsDTO.setArticle("new article for new page");
        articleForNewsDTO.setDate(new Date());
        articleForNewsDTO.setDescription("description");
        this.mockMvc.perform(post("/articles/new/article").flashAttr("article", articleForNewsDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnArticlePageWithStatusOkWhenSaveChanges() throws Exception {
        ArticleForPageDTO articleForPageDTO = new ArticleForPageDTO();
        articleForPageDTO.setId(1L);
        articleForPageDTO.setArticle("article");
        articleForPageDTO.setDescription("description");
        articleForPageDTO.setDate(new Date());
        UserForUiDTO userForUiDTO = new UserForUiDTO();
        userForUiDTO.setId(1L);
        userForUiDTO.setName("name");
        userForUiDTO.setLastname("lastname");
        articleForPageDTO.setUserForUiDTO(userForUiDTO);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setContent("comment");
        commentDTO.setDate(new Date());
        commentDTO.setUser(userForUiDTO);
        List<CommentDTO> commentDTOList = Collections.singletonList(commentDTO);
        articleForPageDTO.setCommentDTOList(commentDTOList);
        this.mockMvc.perform(post("/articles/article/saveChanges").flashAttr("article", articleForPageDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }
}
