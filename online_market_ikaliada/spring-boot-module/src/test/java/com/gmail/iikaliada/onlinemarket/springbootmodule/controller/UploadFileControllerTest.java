package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.SELLER_AUTHORITY_CONSTANT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadFileControllerTest {

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
    public void shouldReturnRedirectedPageToUsers() throws Exception {
        File file = ResourceUtils.getFile("classpath:uploadtest/items.xml");
        InputStream inputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        String fileAsString = stringBuilder.toString();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(),
                "multipart/form-data", fileAsString.getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/private/items"));
    }

    @Test
    @WithMockUser(authorities = SELLER_AUTHORITY_CONSTANT)
    public void shouldReturnRedirectedPageToItemWhenFileNotChosen() throws Exception {
        String fileName = "";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName,
                "multipart/form-data", "item".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/private/items"));
    }
}

