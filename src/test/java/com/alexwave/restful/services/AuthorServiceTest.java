package com.alexwave.restful.services;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.mappers.AuthorMapper;
import com.alexwave.restful.mappers.PaperMapper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.AuthorListIsEmptyException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @InjectMocks
    @Spy
    private AuthorService underTest;

    @Mock
    private AuthorRepository authorRepositoryMock;

    @Mock
    private AuthorMapper authorMapperMock;

    @Mock
    private PaperMapper paperMapperMock;

    @Test
    void testFindAll() {
        List<Author> authorList = Instancio.createList(Author.class);
        List<AuthorDTO> authorDTOList = Instancio.createList(AuthorDTO.class);

        doReturn(authorList).when(authorRepositoryMock).findAll();
        doReturn(authorDTOList).when(authorMapperMock).authorsToAuthorDTOs(authorList);

        assertEquals(authorDTOList, underTest.findAll());
    }

    @Test
    void testFindAllThrowsException() {
        assertThrows(AuthorListIsEmptyException.class, () -> underTest.findAll());
    }

    @Test
    void testFindById() {
        Author author = Instancio.create(Author.class);
        Optional<Author> authorOptional = Optional.of(author);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);

        doReturn(authorOptional).when(authorRepositoryMock).findById(author.getId());
        doReturn(authorDTO).when(authorMapperMock).authorToAuthorDTO(author);

        assertEquals(authorDTO, underTest.findById(authorOptional.get().getId()));
    }

    @Test
    void testFindByIdThrowsException() {
        assertThrows(AuthorIdNotFoundException.class, () -> underTest.findById(1));
    }

    @Test
    void testSave() {
        Author author = Instancio.create(Author.class);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);

        doReturn(author).when(authorMapperMock).authorDTOToAuthor(authorDTO);
        author.setName(authorDTO.getName());
        authorRepositoryMock.save(author);
        verify(authorRepositoryMock).save(author);
        doReturn(authorDTO).when(authorMapperMock).authorToAuthorDTO(author);

        assertNotNull(underTest.save(authorDTO));
    }

    @Test
    void testUpdate() {
        Author author = Instancio.create(Author.class);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);
        Optional<Author> authorOptional = Optional.of(author);

        doReturn(authorOptional).when(authorRepositoryMock).findById(author.getId());
        doReturn(author).when(authorMapperMock).authorDTOToAuthor(authorDTO);
        author.setName(authorDTO.getName());
        authorRepositoryMock.save(author);
        verify(authorRepositoryMock).save(author);
        doReturn(authorDTO).when(authorMapperMock).authorToAuthorDTO(author);

        assertNotNull(underTest.updateById(author.getId(), authorDTO));
    }

    @Test
    void testUpdateThrowsException() {
        assertThrows(AuthorIdNotFoundException.class, () -> underTest.updateById(1, new AuthorDTO()));
    }

    @Test
    void testDelete() {
        Author author = Instancio.create(Author.class);
        Optional<Author> authorOptional = Optional.of(author);

        doReturn(authorOptional).when(authorRepositoryMock).findById(author.getId());

        underTest.deleteById(authorOptional.get().getId());
        verify(authorRepositoryMock).deleteById(authorOptional.get().getId());

        assertNull(underTest.findById(authorOptional.get().getId()));
    }

    @Test
    void testDeleteThrowsException() {
        assertThrows(AuthorIdNotFoundException.class, () -> underTest.deleteById(1));
    }

    @Test
    void testFindPapersByAuthorId() {
        Author author = Instancio.create(Author.class);
        Optional<Author> authorOptional = Optional.of(author);
        List<Paper> papers = author.getPapers();
        List<PaperDTO> paperDTOS = paperMapperMock.papersToPaperDTOs(papers);

        doReturn(authorOptional).when(authorRepositoryMock).findById(author.getId());
        doReturn(paperDTOS).when(paperMapperMock).papersToPaperDTOs(papers);

        assertEquals(underTest.findPapersByAuthorId(author.getId()), paperDTOS);
    }

    @Test
    void testFindPapersByAuthorIdThrowsOuterException() {
        assertThrows(AuthorIdNotFoundException.class, () -> underTest.findPapersByAuthorId(1));
    }
}
