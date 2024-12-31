package com.alexwave.restful.controllers;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.models.Author;
import com.alexwave.restful.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// FIXME add integration and/or unit tests
// FIXME add GlobalControllerAdvice

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.findAll();

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(value = "id") int id) {
        AuthorDTO authorDTO = authorService.findById(id);

        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody Author author) {
        AuthorDTO authorDTO  = authorService.save(author);

        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable(value = "id") int id, @RequestBody Author author) {
        AuthorDTO authorDTO = authorService.update(id, author);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable(value = "id") int id) {
        authorService.deleteById(id);

        return new ResponseEntity<>("Author with id " + id + " was deleted", HttpStatus.OK);
    }
}
