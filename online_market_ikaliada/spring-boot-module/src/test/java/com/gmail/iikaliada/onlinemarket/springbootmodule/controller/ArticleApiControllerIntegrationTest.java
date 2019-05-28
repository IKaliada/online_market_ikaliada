package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleApiControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void shouldGetStatusOkForArticleApi() throws Exception {
        mvc.perform(get("/api/v1/articles"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatusOkForArticleApiWhenGetById() throws Exception {
        mvc.perform(get("/api/v1/articles/article/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveSucceedWith200ForArticleApi() throws Exception {
        mvc.perform(post("/api/v1/articles/article")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"article\":\"New Article\"\n" +
                        ",\"description\":\"I don't now what i'm doing\"\n" +
                        ", \"date\":\"2019-05-19T14:42:31+03:00\"\n" +
                        ", \"userForUiDT\":{\"name\":\"Igar\"\n" +
                        ", \"lastname\": \"Qwerty\"}}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatus200ForArticleApiWhenGetArticle() throws Exception {
        mvc.perform(get("/api/v1/articles/article/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"article\": \"New Article\",\n" +
                        "\"description\": \"I don't now what i'm doing\",\n" +
                        "\"date\": \"2019-05-19T14:42:31+03:00\",\n" +
                        "\"userForUiDTO\": {\n" +
                        "\"id\": 2\n}, \n" +
                        "\"commentDTO\": {\n" +
                        "\"content\": \"Some intresting content\",\n" +
                        "\"date\": \"2019-05-19T14:42:31+03:00\",\n" +
                        "\"user_id\": 1, \n" +
                        "\"article_id\": 1}}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatusOkAfterDeletingArticle() throws Exception {
        mvc.perform(delete("/api/v1/articles/article/1"))
                .andExpect(status().isOk());
    }
}
