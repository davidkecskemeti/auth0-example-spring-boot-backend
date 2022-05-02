package com.dk.auth0examplespringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDto implements Serializable {
    private  int id;
    private String title;
    private boolean completed;
}