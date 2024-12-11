package com.alexwave.restful.controllers;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.models.Author;
import com.alexwave.restful.models.Paper; // FIXME
import com.alexwave.restful.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult; // FIXME
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// FIXME add integration and/or unit tests
// FIXME add GlobalControllerAdvice

@RestController
@RequestMapping("/") // FIXME может перенести сюда "/authors"?
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) { // FIXME use lombok @RequiredArgsConstructor
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAll();
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(authors, HttpStatus.OK);
        }
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable(value = "id") int id) {
        Author author = authorService.findById(id);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // FIXME может NOT_FOUND?
        } else {
            return new ResponseEntity<>(author, HttpStatus.OK);
        }
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorDTO authorDTO) {
        Author author = authorService.save(convertToAuthor(authorDTO));
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable(value = "id") int id, @RequestBody AuthorDTO authorDTO) {
        Author author = authorService.update(id, convertToAuthor(authorDTO));
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(author, HttpStatus.OK);
        }
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable(value = "id") int id) {
        authorService.deleteById(id);
        return new ResponseEntity<>("Author with id " + id + " was deleted", HttpStatus.OK);
    }

//    @PostMapping("/{authorId}/papers")
//    public Author addPaper(@PathVariable int authorId, @RequestBody Paper paper) {
//        return authorService.addPaper(authorId, paper);
//    }
//
//    @DeleteMapping("/{authorId}/papers/{paperId}")
//    public Author removePaper(@PathVariable int authorId, @PathVariable int paperId) {
//        return authorService.removePaper(authorId, paperId);
//    }

    private Author convertToAuthor(AuthorDTO authorDTO) { // FIXME используй mapstruct и проверь новый конвертер через тесты
        Author author = new Author();
        author.setName(authorDTO.getName());
        return author;
    }

}
