package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApiControllerIntegrationTest {
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
    public void shouldGetStatusOkForOrderApiWhenGetAllOrders() throws Exception {
        mvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatusOkForOrderApiWhenGetById() throws Exception {
        mvc.perform(get("/api/v1/orders/36c9b60d-7ef6-4d17-b668-06eac349df1f"))
                .andExpect(status().isOk());
    }
}
