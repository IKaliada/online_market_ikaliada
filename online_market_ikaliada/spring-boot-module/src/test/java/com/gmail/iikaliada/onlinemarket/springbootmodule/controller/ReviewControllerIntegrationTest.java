package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.model.ReviewDTO;
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
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.CUSTOMER_AUTHORITY_CONSTANT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewControllerIntegrationTest {

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
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldGetCommentPageWhenGoToCommentUrl() throws Exception {
        this.mockMvc.perform(post("/private/reviews/changeStatus"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/private/reviews"));
    }

    @Test
    @WithMockUser(authorities = ADMIN_AUTHORITY_CONSTANT)
    public void shouldGetCommentPageWhenDeleteComment() throws Exception {
        this.mockMvc.perform(post("/private/reviews/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/private/reviews"));
    }

    @Test
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldGetReviewCreationPageWhenGoThere() throws Exception {
        this.mockMvc.perform(get("/private/review"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldRedirectToArticlePageWhenSuccessfullySavedReview() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText("test for new review page");
        this.mockMvc.perform(post("/private/review/add").flashAttr("review", reviewDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
    }
}
