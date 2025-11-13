package com.tr.scheduleapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

// Test 용도 : Mvc 체크하고 import 할 것.
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // 선택
public class SchedulePasswordMismatchTest {

    @Autowired
    MockMvc mvc;

    @Test
    void update_should403_whenPasswordMismatch() throws Exception {
        // title t content c author a pw p
        String create = """
            { "title":"t", "content":"c", "author":"a", "password":"p" }
        """;
        String location = mvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(create))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getHeader("Location");

        assertNotNull(location, "Location header must be present"); // 오..
        String id = location.replace("/api/schedules/", "");

        // 403 PasswordMismatchException
        String patchBody="""
            { "title":"t2", "password":"wrong" }
        """;
        mvc.perform(patch("/api/schedules/{id}", id) // req builder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchBody))
                        .andExpect(status().isForbidden());
    }
}