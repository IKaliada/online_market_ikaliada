package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.ItemService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.SELLER_AUTHORITY_CONSTANT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @Before
    public void init() {
        ItemController itemController = new ItemController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageToItems() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(1L);
        itemDTO.setName("item");
        itemDTO.setDescription("some description");
        itemDTO.setPrice(BigDecimal.valueOf(100L));
        itemDTO.setUniqueNumber("d74a8ac4-3b29-424d-a636-69b5447921b1");
        List<ItemDTO> items = Collections.singletonList(itemDTO);
        int pageSize = 1;
        when(itemService.getItems(pageSize)).thenReturn(items);
        this.mockMvc.perform(get("/private/items.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("items", items))
                .andExpect(forwardedUrl("items"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageToItemWhenGetItemById() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(1L);
        itemDTO.setName("item");
        itemDTO.setDescription("some description");
        itemDTO.setPrice(BigDecimal.valueOf(100L));
        itemDTO.setUniqueNumber("d74a8ac4-3b29-424d-a636-69b5447921b1");
        when(itemService.getItemById(itemDTO.getId())).thenReturn(itemDTO);
        this.mockMvc.perform(get("/private/items/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("item", itemDTO))
                .andExpect(forwardedUrl("item"));
    }
}
