package com.example.springbootapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootapp.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {

    private final ObjectMapper objectMapper;

    final DBUtil dbUtil;

    public ApplicationController(ObjectMapper objectMapper, DBUtil dbUtil) {
        this.objectMapper = objectMapper;
        this.dbUtil = dbUtil;
    }

    @GetMapping("/hello")
    public ObjectNode sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {

        dbUtil.createEmployee("1", "John", 25, "Masters");
        dbUtil.showAllEmployees();
        dbUtil.getEmployeeByName("John");

        dbUtil.updateEmployeeAge("Jon", 22);
        dbUtil.getEmployeesByEducation("Masters");

        dbUtil.findCountOfEmployees();
        dbUtil.deleteEmployee("1");
        dbUtil.findCountOfEmployees();
        
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("Name", name);
        return objectNode;
    }
}