package com.alexwave.restful.mapper;

import com.alexwave.AbstractTestClass;
import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.entities.Author;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorMapperTest extends AbstractTestClass {

    @Autowired
    private AuthorMapper authorMapper;

    @Test
    void testAuthorToAuthorDTO() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();

        AuthorDTO authorDTO = authorMapper.authorToAuthorDTO(author);

        assertNotNull(authorDTO);
        assertEquals(author.getName(), authorDTO.getName());
    }

    @Test
    void authorDTOToAuthor() {
        AuthorDTO authorDTO = Instancio.create(AuthorDTO.class);

        Author author = authorMapper.authorDTOToAuthor(authorDTO);

        assertNotNull(author);
        assertEquals(author.getName(), authorDTO.getName());
    }

    @Test
    void authorsToAuthorDTOs() {
        List<Author> authors = Instancio.createList(Author.class);

        List<AuthorDTO> authorDTOs = authorMapper.authorsToAuthorDTOs(authors);

        assertNotNull(authorDTOs);
        assertEquals(authorDTOs.getFirst().getName(), authors.getFirst().getName());
    }

    @Test
    void authorDTOsToAuthors() {
        List<AuthorDTO> authorDTOs = Instancio.createList(AuthorDTO.class);

        List<Author> authors = authorMapper.authorDTOsToAuthors(authorDTOs);

        assertNotNull(authors);
        assertEquals(authorDTOs.getFirst().getName(), authors.getFirst().getName());
    }
}