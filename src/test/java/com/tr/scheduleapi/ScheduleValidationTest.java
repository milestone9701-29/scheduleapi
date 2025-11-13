package com.tr.scheduleapi;


// Test
import org.junit.jupiter.api.Test;

// @Autowired
import org.springframework.beans.factory.annotation.Autowired;

// AutoConfigureMockMvc : Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

// MediaType
import org.springframework.http.MediaType;

// resource - yml <-> properties
import org.springframework.test.context.ActiveProfiles;

// servlet : 언젠가는 보충할 예정.
import org.springframework.test.web.servlet.MockMvc;

// post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// matches
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // 선택
class ScheduleValidationTest {

    @Autowired MockMvc mvc;

    @Test
    void create_should400_whenTitleBlank() throws Exception {
        String body = """
            { "title":"", "content":"c", "author":"a", "password":"p" }
        """;
        mvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                        .andExpect(status().isBadRequest()); // MockMvcResultMatchers.status
    }
}