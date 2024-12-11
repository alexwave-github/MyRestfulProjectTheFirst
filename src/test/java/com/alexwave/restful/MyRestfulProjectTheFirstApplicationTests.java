package com.alexwave.restful;

import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MyRestfulProjectTheFirstApplicationTests {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void contextLoads() {
    }

//    @Test
//    void findAll() {
//        Author author1 = new Author();
//        Author author2 = new Author();
//        List<Author> expectedAuthors = List.of(author1, author2);
//        Mockito.when(authorRepository.findAll()).thenReturn(expectedAuthors);
//
//        List<Author> actualAuthors = authorService.findAll();
//        Assertions.assertEquals(expectedAuthors, actualAuthors);
//    }


}
