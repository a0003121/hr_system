package com.project.HR;

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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //provides a bridge between Spring Boot test features and JUnit
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //configure our runtime environment
@WithMockUser()
public class MockMvcTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
    private MockMvc mockMvc;

    @Test
    public void testGetAllAPI() throws Exception {
        mockMvc.perform(get("/employees")).andExpect(status().isOk());
        mockMvc.perform(get("/depts")).andExpect(status().isOk());
        mockMvc.perform(get("/employeeLeaves")).andExpect(status().isOk());
        mockMvc.perform(get("/insurances")).andExpect(status().isOk());
        mockMvc.perform(get("/leaves")).andExpect(status().isOk());
        mockMvc.perform(get("/positions")).andExpect(status().isOk());
        mockMvc.perform(get("/salaryInfo")).andExpect(status().isOk());
        mockMvc.perform(get("/seats")).andExpect(status().isOk());
    }

//    @Test
//    public void testEmployee() throws Exception {
//        mockMvc.perform(get("/employees")).andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.name").value("emp1"))
//                .andExpect(jsonPath("$.designation").value("manager"))
//                .andExpect(jsonPath("$.empId").value("1"))
//                .andExpect(jsonPath("$.salary").value(3000));
//
//    }

//    mvc.perform(post("/").with(csrf()));
//    mvc.perform(post("/").with(csrf().asHeader()));
//    mvc.perform(post("/").with(csrf().useInvalidToken()));

}
