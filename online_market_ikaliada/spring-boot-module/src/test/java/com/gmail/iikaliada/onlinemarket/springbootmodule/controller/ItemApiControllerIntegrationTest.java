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
public class ItemApiControllerIntegrationTest {

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
    public void shouldGetStatusOkForItemApi() throws Exception {
        mvc.perform(get("/api/v1/private/items"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveSucceedWith200ForItemApi() throws Exception {
        mvc.perform(get("/api/v1/private/items/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSuccessfullySaveItemWith200ForItemAPI() throws Exception {
        mvc.perform(post("/api/v1/private/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"new item\",\n" +
                        "\"price\": 1.21,\n" +
                        "\"description\": \"some new description\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatusOkAfterDeletingItem() throws Exception {
        mvc.perform(delete("/api/v1/private/items/2"))
                .andExpect(status().isOk());
    }
}
