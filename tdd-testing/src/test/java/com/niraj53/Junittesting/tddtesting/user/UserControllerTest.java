package com.niraj53.Junittesting.tddtesting.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niraj53.Junittesting.tddtesting.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Hello World TEST")
    void testHelloWorld() throws Exception {
        String mockResponse = "Hello world 1";
        doReturn(mockResponse).when(userService);

        mockMvc.perform(
                get("/user/helloWorld")
                        .accept(mockResponse, "Hello world 1")
        );
    }

    @Test
    @DisplayName("GET /getUser/1 - Found")
    void getUserById() throws Exception {
        User actualUser = new User(1, "NIRAJ", "DESC");

        when(userService.findById(1))
                .thenReturn(java.util.Optional.of(actualUser));

        MvcResult mvcResult = mockMvc.perform(
                get("/user/findById/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String expectedUser = "{id:1, name:NIRAJ, desc:DESC}";

        /* TEST response by machine with expected result */
        JSONAssert.assertEquals(expectedUser,
                mvcResult.getResponse().getContentAsString(),
                false);
    }

    @Test
    @DisplayName("GET /getUser/1 - Found")
    void getUserByIdWithJSONPath() throws Exception {
        User actualUser = new User(1, "NIRAJ", "DESC");

        doReturn(Optional.of(actualUser)).when(userService).findById(1);

        mockMvc.perform(
                get("/user/findById/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @DisplayName("GET /findById/1 - Not Found")
    void getUserByIdNotFound() throws Exception {
        doReturn(Optional.empty()).when(userService).findById(1);

        mockMvc.perform(
                get("/user/getUserById/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    @Test
    @DisplayName("SAVE_USER /save")
    void save() throws Exception {
        User postUser = new User(1, "NIRAJ", "DESC");
        User mockUser = new User(1, "NIRAJ", "DESC");

        /* Mock Service Layer*/
        doReturn(mockUser).when(userService).save(any());

        /* Make Http Request */
        MvcResult mvcResult = mockMvc.perform(
                post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postUser)))
                .andExpect(status().isCreated())
                .andReturn();

        /* Convert Json Response to Object */
        User actualUser = mapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<User>() {
                }
        );

        assertEquals(actualUser, mockUser);
    }

    @Test
    @DisplayName("SAVE_USER /save - Exception")
    void exceptionWhileSaveUser() throws Exception {
        User postUser = new User(1, "NIRAJ", "DESC");
        doReturn(null).when(userService).save(any());

        mockMvc.perform(
                post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postUser)))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    @DisplayName("DELETE_USER /delete - Success")
    void delete() throws Exception {
        User mockUser = new User(1, "NIRAJ", "DESC");

        /* Mock Service findById and delete */
        doReturn(Optional.of(mockUser)).when(userService).findById(1);
        doReturn(Boolean.TRUE).when(userService).delete(1);

        mockMvc.perform(
                get("/user/delete/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE_USER /delete - Not Found")
    void deleteNotFound() throws Exception {
        doReturn(Optional.empty()).when(userService).findById(1);

        mockMvc.perform(
                get("/user/delete/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE_USER /delete - Failure")
    void deleteFailure() throws Exception {
        User mockUser = new User(1, "NIRAJ", "DESC");
        doReturn(Optional.of(mockUser)).when(userService).findById(1);
        doReturn(Boolean.FALSE).when(userService).delete(1);

        mockMvc.perform(
                get("/user/delete/{id}", 1))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("FIND_ALL_USER /findAll - With Data")
    void findAll() throws Exception {
        User mockUser = new User(1, "NIRAJ", "DESC");
        List<User> users = Collections.singletonList(mockUser);
        doReturn(users).when(userService).findAll();


        mockMvc.perform(
                get("/user/findAll"))
                .andExpect(status().isOk())
                /* Test List of user as we get in response */
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("NIRAJ")))
                .andExpect(jsonPath("$[0].desc", is("DESC")));
    }

    @Test
    @DisplayName("FIND_ALL_USER /findAll - Empty Data")
    void findAllWithEmptyData() throws Exception {
        doReturn(Collections.EMPTY_LIST).when(userService).findAll();

        final MvcResult mvcResult = mockMvc.perform(
                get("/user/findAll"))
                .andExpect(status().isOk()).andReturn();
        List<User> users = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<User>>() {
        });

        /* TEST Response by different ways */
        assertEquals(users, Collections.EMPTY_LIST);
        assertTrue(users.isEmpty());
        assertThat(users, is(empty()));
    }

    private String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
