package com.alexwave.restful.services;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.mapper.AuthorMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
}
