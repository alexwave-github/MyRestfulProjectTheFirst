package com.alexwave.restful.controllers;

import com.alexwave.restful.dto.AuthorDTO;
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
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO authorToSave  = authorService.save(authorDTO);
        return new ResponseEntity<>(authorToSave, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable(value = "id") int id,
                                                  @RequestBody AuthorDTO authorDTO) {
        AuthorDTO authorToUpdate = authorService.updateById(id, authorDTO);
        return new ResponseEntity<>(authorToUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable(value = "id") int id) {
        authorService.deleteById(id);

        return new ResponseEntity<>("Author with id " + id + " was deleted", HttpStatus.OK);
    }
}
