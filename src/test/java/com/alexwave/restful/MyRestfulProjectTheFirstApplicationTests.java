package com.alexwave.restful;

import com.alexwave.AbstractTestClass;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;



class MyRestfulProjectTheFirstApplicationTests extends AbstractTestClass {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void contextLoads() {
    }
}
