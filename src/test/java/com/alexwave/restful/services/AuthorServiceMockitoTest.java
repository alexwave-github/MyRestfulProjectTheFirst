package com.alexwave.restful.services;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.mapper.AuthorMapper;
import com.alexwave.restful.mapper.PaperMapper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.AuthorListIsEmptyException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceMockitoTest {

    @InjectMocks
    @Spy
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private PaperMapper paperMapper;

    @Test
    void testFindAll() {
        List<Author> authorList = Instancio.createList(Author.class);
        List<AuthorDTO> authorDTOList = Instancio.createList(AuthorDTO.class);

        doReturn(authorList).when(authorRepository).findAll();
        doReturn(authorDTOList).when(authorMapper).authorsToAuthorDTOs(authorList);

        assertEquals(authorDTOList, authorService.findAll());
    }

    @Test
    void testFindAllThrowsException() {
        assertThrows(AuthorListIsEmptyException.class, () -> authorService.findAll());
    }

    @Test
    void testFindById() {
        Author author = Instancio.create(Author.class);
        Optional<Author> authorOptional = Optional.of(author);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);

        doReturn(authorOptional).when(authorRepository).findById(author.getId());
        doReturn(authorDTO).when(authorMapper).authorToAuthorDTO(author);

        assertEquals(authorDTO, authorService.findById(authorOptional.get().getId()));
    }

    @Test
    void testFindByIdThrowsException() {
        assertThrows(AuthorIdNotFoundException.class, () -> authorService.findById(1));
    }

    @Test
    void testSave() {
        Author author = Instancio.create(Author.class);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);

        doReturn(author).when(authorMapper).authorDTOToAuthor(authorDTO);
        author.setName(authorDTO.getName());
        authorRepository.save(author);
        verify(authorRepository).save(author);
        doReturn(authorDTO).when(authorMapper).authorToAuthorDTO(author);

        assertNotNull(authorService.save(authorDTO));
    }

    @Test
    void testUpdate() {
        Author author = Instancio.create(Author.class);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);
        Optional<Author> authorOptional = Optional.of(author);

        doReturn(authorOptional).when(authorRepository).findById(author.getId());
        doReturn(author).when(authorMapper).authorDTOToAuthor(authorDTO);
        author.setName(authorDTO.getName());
        authorRepository.save(author);
        verify(authorRepository).save(author);
        doReturn(authorDTO).when(authorMapper).authorToAuthorDTO(author);

        assertNotNull(authorService.updateById(author.getId(), authorDTO));
    }

    @Test
    void testUpdateThrowsException() {
        assertThrows(AuthorIdNotFoundException.class, () -> authorService.updateById(1, new AuthorDTO()));
    }

    @Test
    void testDelete() {
        Author author = Instancio.create(Author.class);
        Optional<Author> authorOptional = Optional.of(author);

        doReturn(authorOptional).when(authorRepository).findById(author.getId());

        authorService.deleteById(authorOptional.get().getId());
        verify(authorRepository).deleteById(authorOptional.get().getId());

        assertNull(authorService.findById(authorOptional.get().getId()));
    }

    @Test
    void testDeleteThrowsException() {
        assertThrows(AuthorIdNotFoundException.class, () -> authorService.deleteById(1));
    }

    @Test
    void testFindPapersByAuthorId() {
        Author author = Instancio.create(Author.class);
        Optional<Author> authorOptional = Optional.of(author);
        List<Paper> papers = author.getPapers();
        List<PaperDTO> paperDTOS = paperMapper.papersToPaperDTOs(papers);

        doReturn(authorOptional).when(authorRepository).findById(author.getId());
        doReturn(paperDTOS).when(paperMapper).papersToPaperDTOs(papers);

        assertEquals(authorService.findPapersByAuthorId(author.getId()), paperDTOS);
    }

    @Test
    void testFindPapersByAuthorIdThrowsException() {
        assertThrows(AuthorIdNotFoundException.class, () -> authorService.findPapersByAuthorId(1))
                .addSuppressed(new PaperListIsEmptyException());
    }
}
