package com.example.resttutorial;

import com.example.resttutorial.Components.UserInfo;
import com.example.resttutorial.Entities.Employee;
import com.example.resttutorial.Repositories.EmployeeRepository;
import com.example.resttutorial.Repositories.RoleRepository;
import com.example.resttutorial.Repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.Charset;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class RestTutorialApplicationTests {

    @Autowired
    MockMvc mvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void testAddEmployee() throws Exception {
        Employee test = new Employee("Test", "Testee");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String asJSON = ow.writeValueAsString(test);

        mvc.perform(post("/api/add").contentType(APPLICATION_JSON_UTF8).content(asJSON)).andExpect(status().isOk());
    }

    @Test
    void testGetEmployees() throws Exception {
        mvc.perform(get("/api/employees").contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    void testGetUsers() throws Exception {
        mvc.perform(get("/login/users").contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    void testAddUsers() throws Exception {

        UserInfo test = new UserInfo(UUID.randomUUID().toString(), "abc");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String asJSON = ow.writeValueAsString(test);

        mvc.perform(post("/login/register").contentType(APPLICATION_JSON_UTF8).content(asJSON)).andExpect(status().isOk());
    }
}
