package com.example.resttutorial.Controllers;

import com.example.resttutorial.Repositories.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestNormal {
    private EmployeeRepository repository;

    public TestNormal(EmployeeRepository repository){
        this.repository = repository;
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }


}
