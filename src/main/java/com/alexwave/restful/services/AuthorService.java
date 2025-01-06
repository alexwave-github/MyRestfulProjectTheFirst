package com.alexwave.restful.services;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.util.exception_handling.AuthorIdNotFoundException;
import com.alexwave.restful.util.exception_handling.EmptyListException;
import com.alexwave.restful.util.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// FIXME add integration and unit tests

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorsRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorDTO> findAll() {
        List<AuthorDTO> authorDTOS = authorMapper.authorsToAuthorDTOs(authorsRepository.findAll());
        if (authorDTOS.isEmpty()) {
            throw new EmptyListException("Authors do not exist yet!");
        } else {

            return authorDTOS;
        }
    }

    public AuthorDTO findById(int id) {
        Optional<Author> author = authorsRepository.findById(id);
        if (author.isPresent()) {
            AuthorDTO authorDTO = authorMapper.authorToAuthorDTO(author.get()); // оставил для наглядности
            return authorDTO;
        } else {
            throw new AuthorIdNotFoundException();
        }
    }

    @Transactional
    public AuthorDTO save(Author author) {
        authorsRepository.save(author);
        AuthorDTO authorDTO = authorMapper.authorToAuthorDTO(author); // оставил для наглядности

        return authorDTO;
    }

    @Transactional
    public AuthorDTO updateById(int id, Author author) {
        Optional<Author> authorOptional = authorsRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author authorToUpdate = authorOptional.get();
            authorToUpdate.setName(author.getName());
            authorsRepository.save(authorToUpdate);
            AuthorDTO authorDTO = authorMapper.authorToAuthorDTO(authorToUpdate);// оставил для наглядности
            return authorDTO;
        } else {
            throw new AuthorIdNotFoundException();
        }
    }

    @Transactional
    public void deleteById(int id) {
        authorsRepository.deleteById(id);
    }

}
