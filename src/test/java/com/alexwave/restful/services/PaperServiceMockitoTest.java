package com.alexwave.restful.services;

import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.mapper.PaperMapper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.repositories.PaperRepository;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.PaperIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.PaperListIsEmptyException;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        assertThrows(PaperListIsEmptyException.class, () -> paperService.findAll());
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
    public void testFindByIdThrowsException() {
        assertThrows(PaperIdNotFoundException.class, () -> paperService.findById(1));
    }

    @Test
    public void testSave() {
        Author author = Instancio.create(Author.class);
        Optional<Author> optionalAuthor = Optional.of(author);
        Paper paper = Instancio.create(Paper.class);
        PaperDTO paperDTO = Instancio.create(PaperDTO.class);

        doReturn(optionalAuthor).when(authorRepository).findById(author.getId());
        doReturn(paper).when(paperMapper).paperDTOToPaper(paperDTO);
        paper.setAuthor(author);
        author.getPapers().add(paper);

        doReturn(paper).when(paperRepository).save(paper);
        doReturn(author).when(authorRepository).save(author);
        doReturn(paperDTO).when(paperMapper).paperToPaperDTO(paper);


        assertNotNull(paperService.save(paperDTO, author.getId()));
    }

    @Test
    public void testSaveThrowsException() {
        assertThrows(AuthorIdNotFoundException.class, () -> paperService.save(new PaperDTO(), 1));
    }

    @Test
    public void testUpdateById() {
        Paper paper = Instancio.create(Paper.class);
        PaperDTO paperDTO = Instancio.create(PaperDTO.class);
        Optional<Paper> optionalPaper = Optional.of(paper);

        doReturn(optionalPaper).when(paperRepository).findById(paper.getId());
        doReturn(paper).when(paperMapper).paperDTOToPaper(paperDTO);
        Paper updatedPaper = optionalPaper.get();
        updatedPaper.setTitle(paperDTO.getTitle());
        updatedPaper.setContent(paperDTO.getContent());
        updatedPaper.setDateForPublishing(paperDTO.getDateForPublishing());
        paperRepository.save(updatedPaper);
        verify(paperRepository).save(updatedPaper);
        doReturn(paperDTO).when(paperMapper).paperToPaperDTO(paper);

        assertNotNull(paperService.updateById(paper.getId(), paperDTO));
    }

    @Test
    public void testUpdateByIdThrowsException() {
        assertThrows(PaperIdNotFoundException.class, () -> paperService.updateById(1, new PaperDTO()));
    }

    @Test
    public void testDeleteById() {
        Paper paper = Instancio.create(Paper.class);
        Optional<Paper> optionalPaper = Optional.of(paper);

        doReturn(optionalPaper).when(paperRepository).findById(paper.getId());
        Paper paperToDelete = optionalPaper.get();

        paperService.deleteById(paperToDelete.getId());
        verify(paperRepository).delete(paperToDelete);

        assertNull(paperService.findById(paperToDelete.getId()));
    }

    @Test
    public void testDeleteByIdThrowsException() {
        assertThrows(PaperIdNotFoundException.class, () -> paperService.deleteById(1));
    }
}
