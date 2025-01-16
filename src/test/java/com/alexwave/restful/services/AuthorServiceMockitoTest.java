package com.alexwave.restful.services;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.mapper.AuthorMapper;
import com.alexwave.restful.repositories.AuthorRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceMockitoTest {

    @InjectMocks
    @Spy
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @Test
    void testFindAll() {
        List<Author> authorList = Instancio.createList(Author.class);
        List<AuthorDTO> authorDTOList = Instancio.createList(AuthorDTO.class);

        doReturn(authorList).when(authorRepository).findAll();
        doReturn(authorDTOList).when(authorMapper).authorsToAuthorDTOs(authorList);

        assertEquals(authorDTOList, authorService.findAll());
    }

    @Test
    void testFindById() {
        Author author = Instancio.create(Author.class);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);

        doReturn(Optional.of(author)).when(authorRepository).findById(author.getId());
        doReturn(authorDTO).when(authorMapper).authorToAuthorDTO(author);

        assertEquals(authorDTO, authorService.findById(author.getId()));
    }

    @Test
    void testSave() {
        Author author = Instancio.create(Author.class);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);

        doReturn(author).when(authorMapper).authorDTOToAuthor(authorDTO);
        doReturn(author).when(authorRepository).save(author);

        assertEquals(authorMapper.authorToAuthorDTO(author), authorService.save(authorDTO));
    }

    @Test
    void testUpdate() {
        Author author = Instancio.create(Author.class);
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);
        Optional<Author> authorOptional = Optional.of(author);

        doReturn(authorOptional).when(authorRepository).findById(author.getId());
        doReturn(author).when(authorMapper).authorDTOToAuthor(authorDTO);
        doReturn(author).when(authorRepository).save(authorOptional.get());

        assertEquals(authorMapper.authorToAuthorDTO(author), authorService.updateById(author.getId(), authorDTO));
    }

//    ToDo
//     - сделать тест deleteById (а нужен ли он?)
//     - сделать тест findPapersByAuthorId
}
