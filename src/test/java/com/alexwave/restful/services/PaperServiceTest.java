package com.alexwave.restful.services;

import com.alexwave.AbstractTestClass;
import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.mapper.PaperMapper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.repositories.PaperRepository;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.PaperIdNotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PaperServiceTest extends AbstractTestClass {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private PaperService paperService;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private AuthorRepository authorRepository;

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
    void testFindById() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();
        Paper savedPaper = paperRepository.save(paper);

        paperService.findById(savedPaper.getId());

        assertThat(paper.getId()).isEqualTo(savedPaper.getId());
    }

    @Test
    void testFindByIdThrowsException() {
        assertThrows(PaperIdNotFoundException.class, () -> paperService.findById(1000));
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
        assertThrows(AuthorIdNotFoundException.class, () -> paperService.save(new PaperDTO(), 1000));
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
        assertThrows(PaperIdNotFoundException.class, () -> paperService.updateById(1000, new PaperDTO()));
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
        assertThrows(PaperIdNotFoundException.class, () -> paperService.findById(1000));
    }
}