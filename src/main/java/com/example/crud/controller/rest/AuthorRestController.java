package com.example.crud.controller.rest;

import com.example.crud.dto.AuthorDto;
import com.example.crud.entity.Author;
import com.example.crud.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<Author> authors = authorService.findAll();
        List<AuthorDto> authorDtos = authors.stream()
                .map(author -> new AuthorDto(author.getId(), author.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        return authorService.findById(id)
                .map(author -> new AuthorDto(author.getId(), author.getName()))
                .map(authorDto -> new ResponseEntity<>(authorDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        Author authorToCreate = new Author();
        authorToCreate.setName(authorDto.getName());

        Author savedAuthor = authorService.save(authorToCreate);

        AuthorDto createdDto = new AuthorDto(savedAuthor.getId(), savedAuthor.getName());
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody AuthorDto authorDto) {
        Author authorDetails = new Author();
        authorDetails.setName(authorDto.getName());

        try {
            Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
            AuthorDto updatedDto = new AuthorDto(updatedAuthor.getId(), updatedAuthor.getName());
            return new ResponseEntity<>(updatedDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        if (authorService.findById(id).isPresent()) {
            authorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}