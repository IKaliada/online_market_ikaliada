package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.CUSTOMER_AUTHORITY_CONSTANT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {

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
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldGetOrdersPageWhenGetAllOrders() throws Exception {
        this.mockMvc.perform(get("/private/orders"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldAddOrderToDatabaseAndReturnStatusOk() throws Exception {
        this.mockMvc.perform(post("/private/orders/1/add").param("quantity", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/private/orders/find"));
    }

    @Test
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldShowAllOrdersForOneUserAndReturnOrdersPage() throws Exception {
        this.mockMvc.perform(get("/private/orders/find"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = CUSTOMER_AUTHORITY_CONSTANT)
    public void shouldSucceedReturnStatusOkWhenGetOrderByUId() throws Exception {
        this.mockMvc.perform(get("/private/orders/36c9b60d-7ef6-4d17-b668-06eac349df1f"))
                .andExpect(status().isOk());
    }
}
