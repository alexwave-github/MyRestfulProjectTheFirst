package com.alexwave.restful.services;

import com.alexwave.AbstractTestClass;
import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.util.my_exceptions.AuthorEmptyListException;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.mapper.AuthorMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthorServiceTest extends AbstractTestClass {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

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
        assertThat(catchThrowableOfType(() -> authorService.findAll(), AuthorEmptyListException.class));
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
        assertThat(catchThrowableOfType(() -> authorService.findById(1000), AuthorIdNotFoundException.class));
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
                .ignore(field(Author::getId))
                .ignore(field(Author::getPapers)).create();
        authorRepository.save(author);

        AuthorDTO authorDTO = authorService.findById(author.getId());
        authorDTO.setName(authorDTO.getName() + " Updated");

        assertThat(authorDTO.getName()).isEqualTo(author.getName() + " Updated");
    }

    @Test
    void testUpdateByIdThrowsException() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        AuthorDTO authorDTO = authorMapper.authorToAuthorDTO(author);

        assertThat(catchThrowableOfType(() -> authorService.updateById(1000, authorDTO), AuthorIdNotFoundException.class));

    }


    @Test
    void testDeleteById() {
        Author author = Instancio.of(Author.class)
                .ignore(field(Author::getId)).ignore(field(Author::getPapers)).create();
        Author savedAuthor = authorRepository.save(author);

        authorService.deleteById(savedAuthor.getId());

        assertThat(authorRepository.findById(savedAuthor.getId()).isEmpty()).isTrue();

    }

    @Test
    void testDeleteByIdThrowsException() {
        assertThrows(AuthorIdNotFoundException.class,
                () -> authorService.findById(1));
    }
}