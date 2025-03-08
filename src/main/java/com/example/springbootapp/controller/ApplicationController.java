package com.example.springbootapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/hello")
    public ObjectNode sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("Name", name);
        return objectNode;
    }

}