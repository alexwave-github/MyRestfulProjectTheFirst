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
class PaperControllerTest extends AbstractTestClass {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        paperRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void getAllPapers() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();

        paperRepository.save(paper);
        mockMvc.perform(get("/papers"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getPaperById() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();

        paperRepository.save(paper);
        mockMvc.perform(get("/papers/" + paper.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void createPaper() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();

        authorRepository.save(author);
        paperRepository.save(paper);
        paper.setAuthor(author);
        author.setPapers(List.of(paper));

        String paperJson = objectMapper.writeValueAsString(paper);
        mockMvc.perform(post("/papers/author/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(paperJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void updatePaper() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();

        paperRepository.save(paper);

        String paperJson = objectMapper.writeValueAsString(paper);
        mockMvc.perform(put("/papers/" + paper.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(paperJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void deletePaper() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();

        paperRepository.save(paper);

        mockMvc.perform(delete("/papers/" + paper.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}