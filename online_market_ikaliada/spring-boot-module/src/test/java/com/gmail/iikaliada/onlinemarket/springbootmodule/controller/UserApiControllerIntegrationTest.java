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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerIntegrationTest {

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
    public void shouldGetStatusOkForUserApi() throws Exception {
        mvc.perform(get("/api/v1/private/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveSucceedWith200ForUserApi() throws Exception {
        mvc.perform(post("/api/v1/private/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("[{'name':'username'" +
                        ",'middlename':'middlename'" +
                        ", 'lastname':'lastname'" +
                        ", 'email':'email@email.com'" +
                        ", 'password':'123456'" +
                        ", 'role':['name':'name']}]"));
    }
}
