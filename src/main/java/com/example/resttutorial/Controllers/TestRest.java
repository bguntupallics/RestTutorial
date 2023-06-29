package com.example.resttutorial.Controllers;
import com.example.resttutorial.Components.GPTResponse;
import com.example.resttutorial.Entities.Employee;
import com.example.resttutorial.Repositories.EmployeeRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/api")
public class TestRest {

    @Autowired
    RestTemplate restTemplate;
    private final EmployeeRepository repository;

    public TestRest(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/employees")
    public List<Employee> all(){
        return repository.findAll();
    }

    @GetMapping("/employees/find/{name}")
    public List<Employee> findName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> update(@PathVariable long id, @RequestBody Employee updated){
        Employee to_change = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found"));
        to_change.setName(updated.getName());
        to_change.setPosition(updated.getPosition());
        repository.save(to_change);
        return ResponseEntity.ok(to_change);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addPerson(@RequestBody Employee details){
        Employee to_add = new Employee(details.getName(), details.getPosition());
        repository.save(to_add);
        return ResponseEntity.ok(to_add);
    }

    @DeleteMapping("/delete/name/{name}")
    public String deleteName(@PathVariable String name){
        List<Employee> to_delete = repository.findByName(name);
        Employee chosen = to_delete.get(0);
        repository.delete(chosen);
        return "Done";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id){
        Employee to_delete = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee does not exist"));
        repository.delete(to_delete);
        return "Done";
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt){
        String apiKey = "KEY HERE";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        HashMap<String, String> messages = new HashMap<>();
        messages.put("role", "system");
        messages.put("content", prompt);
        body.put("messages", new HashMap[]{messages});
        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
        ResponseEntity<GPTResponse> response = restTemplate.exchange("https://api.openai.com/v1/chat/completions", HttpMethod.POST, entity, GPTResponse.class);
        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}
