package com.alexwave.restful.services;

import com.alexwave.AbstractTestClass;
import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.repositories.PaperRepository;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.PaperIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.PaperListIsEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.instancio.Select.field;

@Slf4j
@SpringBootTest
class PaperServiceTest extends AbstractTestClass {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private PaperService paperService;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        paperRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        Paper paper = new Paper();
        paper.setTitle("Paper Title");
        paper.setContent("Paper Content");
        paper.setDateForPublishing(Instant.now());
        paperRepository.save(paper);

        List<PaperDTO> paperDTOs = paperService.findAll();

        assertThat(paperDTOs).isNotEmpty();
    }

    @Test
    void testFindAllThrowsException() {
        catchThrowableOfType(() -> paperService.findAll(), PaperListIsEmptyException.class);
    }

    @Test
    void testFindById() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();
        Paper savedPaper = paperRepository.save(paper);

        paperService.findById(savedPaper.getId());

        assertThat(paper.getId()).isEqualTo(savedPaper.getId());
    }

    @Test
    void testFindByIdThrowsException() {
        catchThrowableOfType(() -> paperService.findById(1000), PaperIdNotFoundException.class);
    }

    @Test
    void testSave() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        authorRepository.save(author);

        paper.setAuthor(author);
        paperRepository.save(paper);

        PaperDTO paperDTO = paperService.findById(paper.getId());

        assertThat(paperDTO).isNotNull();
        assertThat(paper.getClass().getFields())
                .isEqualTo(paperDTO.getClass().getFields());
    }

    @Test
    void testSaveThrowsException() {
        catchThrowableOfType(() -> paperService.save(new PaperDTO(), 1000), AuthorIdNotFoundException.class);
    }

    @Test
    void testUpdateById() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();
        paperRepository.save(paper);

        PaperDTO paperDTO = paperService.findById(paper.getId());
        paperDTO.setTitle(paper.getTitle() + " Updated");
        paperDTO.setContent(paper.getContent() + " Updated");

        assertThat(paperDTO).isNotNull();
        assertThat(paperDTO.getTitle()).isEqualTo(paper.getTitle() + " Updated");
        assertThat(paperDTO.getContent()).isEqualTo(paper.getContent() + " Updated");
    }

    @Test
    void testUpdateByIdThrowsException() {
        catchThrowableOfType(() -> paperService.updateById(1000, new PaperDTO()), PaperIdNotFoundException.class);
    }

    @Test
    void testDeleteById() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();
        Paper savedPaper = paperRepository.save(paper);

        paperService.deleteById(savedPaper.getId());

        assertThat(paperRepository.findById(savedPaper.getId())).isEmpty();
    }

    @Test
    void testDeleteByIdThrowsException() {
        catchThrowableOfType(() -> paperService.deleteById(1000), PaperIdNotFoundException.class);
    }
}