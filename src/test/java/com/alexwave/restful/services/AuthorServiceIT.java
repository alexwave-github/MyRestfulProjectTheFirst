package com.alexwave.restful.services;

import com.alexwave.AbstractTestClass;
import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.mappers.PaperMapper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.AuthorListIsEmptyException;
import com.alexwave.restful.util.my_exceptions.PaperListIsEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.instancio.Select.field;

@Slf4j
@SpringBootTest
class AuthorServiceIT extends AbstractTestClass {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private PaperMapper paperMapper;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        Author author = new Author();
        author.setName("Author");
        authorRepository.save(author);

        List<AuthorDTO> authorDTOS = authorService.findAll();

        assertThat(authorDTOS).isNotEmpty();
    }

    @Test
    void testFindAllThrowsException() {
        assertThat(catchThrowableOfType(AuthorListIsEmptyException.class, () -> authorService.findAll())).isNotNull();
    }

    @Test
    void testFindById() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        Author savedAuthor = authorRepository.save(author);

        authorService.findById(savedAuthor.getId());

        assertThat(author.getId()).isEqualTo(savedAuthor.getId());
    }

    @Test
    void testFindByIdThrowsException() {
        assertThat(catchThrowableOfType(AuthorIdNotFoundException.class, ()-> authorService.findById(1000))).isNotNull();
    }

    @Test
    void testSave() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        authorRepository.save(author);

        AuthorDTO authorDTO = authorService.findById(author.getId());

        assertThat(authorDTO).isNotNull();
        assertThat(author.getName()).isEqualTo(authorDTO.getName());
    }

    @Test
    void testUpdateById() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        authorRepository.save(author);

        AuthorDTO authorDTO = authorService.findById(author.getId());
        authorDTO.setName(authorDTO.getName() + " Updated");

        assertThat(authorDTO).isNotNull();
        assertThat(authorDTO.getName()).isEqualTo(author.getName() + " Updated");
    }

    @Test
    void testUpdateByIdThrowsException() {
        assertThat(catchThrowableOfType(AuthorIdNotFoundException.class, () -> authorService.updateById(1000, new AuthorDTO()))).isNotNull();
    }


    @Test
    void testDeleteById() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        Author savedAuthor = authorRepository.save(author);

        authorService.deleteById(savedAuthor.getId());

        assertThat(authorRepository.findById(savedAuthor.getId()).isEmpty());

    }

    @Test
    void testDeleteByIdThrowsException() {
        assertThat(catchThrowableOfType(AuthorIdNotFoundException.class, () -> authorService.deleteById(1000))).isNotNull();
    }

    @Test
    void testFindPapersByAuthorId() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        authorRepository.save(author);
        List<Paper> papers = new ArrayList<>();
        Paper paper = new Paper();
        paper.setTitle("Paper Title");
        paper.setContent("Paper Content");
        paper.setDateForPublishing(Instant.now());
        paperService.save(paperMapper.paperToPaperDTO(paper), author.getId());
        papers.add(paper);

        author.setPapers(papers);
        Author savedAuthor = authorRepository.save(author);


        List<PaperDTO> paperDTOs = authorService.findPapersByAuthorId(savedAuthor.getId());

        assertThat(paperDTOs).isNotEmpty();
    }

    @Test
    void testFindPapersByAuthorIdThrowsException() {
        assertThat(catchThrowableOfType(AuthorIdNotFoundException.class, () -> authorService.findPapersByAuthorId(1000))).isNotNull();
    }

    @Test
    void testFindPapersByAuthorIdThrowsInnerException() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        Author savedAuthor = authorRepository.save(author);

        assertThat(catchThrowableOfType(PaperListIsEmptyException.class, () -> authorService.findPapersByAuthorId(savedAuthor.getId()))).isNotNull();
    }

}