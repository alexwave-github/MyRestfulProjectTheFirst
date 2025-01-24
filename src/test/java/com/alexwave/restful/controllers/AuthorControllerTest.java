package com.alexwave.restful.controllers;

import com.alexwave.AbstractTestClass;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.repositories.PaperRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthorControllerTest extends AbstractTestClass {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void getAllAuthorsTest() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();

        authorRepository.save(author);
        mockMvc.perform(get("/authors"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getAuthorByIdTest() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();

        authorRepository.save(author);
        mockMvc.perform(get("/authors/" + author.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void createAuthorTest() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();

        String authorJson = objectMapper.writeValueAsString(author);
        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void updateAuthorTest() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();

        authorRepository.save(author);

        String authorJson = objectMapper.writeValueAsString(author);
        mockMvc.perform(put("/authors/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void deleteAuthorTest() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();

        authorRepository.save(author);
        mockMvc.perform(delete("/authors/" + author.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getPapersOfThisAuthorTest() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();

        paperRepository.save(paper);
        authorRepository.save(author);
        paper.setAuthor(author);
        author.setPapers(List.of(paper));
        paperRepository.save(paper);
        authorRepository.save(author);

        mockMvc.perform(get("/authors/" + author.getId() + "/papers"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}