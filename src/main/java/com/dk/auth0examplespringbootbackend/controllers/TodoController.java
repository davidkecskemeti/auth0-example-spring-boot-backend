package com.dk.auth0examplespringbootbackend.controllers;

import com.dk.auth0examplespringbootbackend.dto.TodoDto;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@PreAuthorize("hasPermission('ANY', 'read')")
public class TodoController {

    @GetMapping(value = "todos")
    public List<TodoDto> getTodos() {
        return Arrays.asList(
                new TodoDto(1, "Learn Typescript", true),
                new TodoDto(1, "Learn Express", false),
                new TodoDto(1, "Learn Node", false)
        );

    }
}