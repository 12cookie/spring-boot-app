package com.example.springbootapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@SpringBootApplication
public class SpringBootAppApplication {

    private final ObjectMapper mapper;

    public SpringBootAppApplication(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppApplication.class, args);
    }

    @GetMapping("/hello")
    public ObjectNode sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("Name", name);
        return objectNode;
    }
}
