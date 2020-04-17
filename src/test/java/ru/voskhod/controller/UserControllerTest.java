package ru.voskhod.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.voskhod.dao.UserDaoImpl;
import ru.voskhod.dto.UserDto;
import ru.voskhod.dto.UserPageDto;
import ru.voskhod.exception.NoSuchUserException;
import ru.voskhod.service.UserService;
import ru.voskhod.util.NetworkRequester;
import ru.voskhod.util.UserJsonParser;
import ru.voskhod.util.UserMapper;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUpp() throws IOException, NoSuchUserException {
        when(userService.downloadPage(1)).thenReturn(new UserPageDto());
        when(userService.downloadUser(1L)).thenReturn(new UserDto());
    }

    @Test
    void getPage() throws Exception {
        mockMvc.perform(get("/users?page=1")).andExpect(status().isOk());
    }

    @Test
    void getUser() throws Exception {
        mockMvc.perform(get("/users/1")).andExpect(status().isOk());
    }
}