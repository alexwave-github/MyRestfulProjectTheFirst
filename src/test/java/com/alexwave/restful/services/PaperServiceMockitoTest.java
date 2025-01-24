package com.alexwave.restful.services;

import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.mapper.PaperMapper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.repositories.PaperRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class PaperServiceMockitoTest {

    @InjectMocks
    @Spy
    private PaperService paperService;

    @Mock
    private PaperRepository paperRepository;

    @Mock
    private PaperMapper paperMapper;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    public void testFindAll() {
        List<Paper> papers = Instancio.createList(Paper.class);
        List<PaperDTO> paperDTOs = Instancio.createList(PaperDTO.class);

        doReturn(papers).when(paperRepository).findAll();
        doReturn(paperDTOs).when(paperMapper).papersToPaperDTOs(papers);

        assertThat(paperDTOs).isNotNull();
        assertEquals(paperDTOs, paperService.findAll());
    }

    @Test
    public void testFindAllThrowsException() {

    }

    @Test
    public void testFindById() {
        Paper paper = Instancio.create(Paper.class);
        PaperDTO paperDTO = Instancio.create(PaperDTO.class);
        Optional<Paper> optionalPaper = Optional.of(paper);

        doReturn(optionalPaper).when(paperRepository).findById(paper.getId());
        doReturn(paperDTO).when(paperMapper).paperToPaperDTO(paper);

        assertEquals(paperDTO, paperService.findById(paper.getId()));
    }

    @Test
    public void testSave() {
        Author author = Instancio.create(Author.class);
        Optional<Author> optionalAuthor = Optional.of(author);
        Paper paper = Instancio.create(Paper.class);
        PaperDTO paperDTO = Instancio.create(PaperDTO.class);

        doReturn(optionalAuthor).when(authorRepository).findById(author.getId());
        doReturn(paper).when(paperMapper).paperDTOToPaper(paperDTO);
        doReturn(paper).when(paperRepository).save(paper);

        assertEquals(paperMapper.paperToPaperDTO(paper), paperService.save(paperDTO, optionalAuthor.get().getId()));
    }

}
