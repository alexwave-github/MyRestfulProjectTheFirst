package com.alexwave.restful.services;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.AuthorListIsEmptyException;
import com.alexwave.restful.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorsRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<AuthorDTO> findAll() {
        List<AuthorDTO> authorDTOS = authorMapper.authorsToAuthorDTOs(authorsRepository.findAll());
        if (authorDTOS.isEmpty()) {
            throw new AuthorListIsEmptyException();
        } else {
            return authorDTOS;
        }
    }

    @Transactional(readOnly = true)
    public AuthorDTO findById(int id) {
        Optional<Author> author = authorsRepository.findById(id);
        if (author.isPresent()) {
            return authorMapper.authorToAuthorDTO(author.get());
        } else {
            throw new AuthorIdNotFoundException();
        }
    }

    @Transactional
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        authorsRepository.save(author);

        return authorMapper.authorToAuthorDTO(author);
    }

    @Transactional
    public AuthorDTO updateById(int id, AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorsRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author authorToUpdate = authorOptional.get();
            authorToUpdate.setName(authorMapper.authorDTOToAuthor(authorDTO).getName());
            authorsRepository.save(authorToUpdate);
            return authorMapper.authorToAuthorDTO(authorToUpdate);
        } else {
            throw new AuthorIdNotFoundException();
        }
    }

    @Transactional
    public void deleteById(int id) {
        authorsRepository.deleteById(id);
    }

}
