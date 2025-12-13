package com.example.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorDto {

    private Long id;

    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(min = 1, max = 100, message = "Имя автора должно быть от 1 до 100 символов")
    private String name;

    public AuthorDto() {}

    public AuthorDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}