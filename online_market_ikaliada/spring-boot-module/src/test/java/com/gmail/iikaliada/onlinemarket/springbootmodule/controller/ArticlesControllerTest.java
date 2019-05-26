package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ArticleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ArticleForPageDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.CommentDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.UserForUiDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.SELLER_AUTHORITY_CONSTANT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ArticlesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArticleService articleService;

    @Before
    public void init() {
        ArticleController articleController = new ArticleController(articleService);
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageToArticle() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setArticle("article");
        articleDTO.setDescription("description");
        articleDTO.setDate(new Date());
        UserForUiDTO userForUiDTO = new UserForUiDTO();
        userForUiDTO.setId(1L);
        userForUiDTO.setName("name");
        userForUiDTO.setLastname("lastname");
        articleDTO.setUserForUiDTO(userForUiDTO);
        List<ArticleDTO> articles = Collections.singletonList(articleDTO);
        int pageSize = 1;
        when(articleService.getArticle(pageSize)).thenReturn(articles);
        this.mockMvc.perform(get("/articles.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("articles", articles))
                .andExpect(forwardedUrl("articles"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldSuccessfullyRedirectToArticlePageWhenFindArticleById() throws Exception {
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
        when(articleService.getArticleById(articleForPageDTO.getId())).thenReturn(articleForPageDTO);
        this.mockMvc.perform(get("/articles/article/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("article", articleForPageDTO))
                .andExpect(forwardedUrl("article"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldSuccessfullyRedirectToArticlePageWhenFindArticleByKeyWord() throws Exception {
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
        List<ArticleForPageDTO> articles = Collections.singletonList(articleForPageDTO);
        String keyWord = "test";
        when(articleService.getArticleByKeyWord(keyWord)).thenReturn(articles);
        this.mockMvc.perform(post("/articles/article/find").param("keyWord", keyWord))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("articles", articles))
                .andExpect(forwardedUrl("articles"));
    }
}
