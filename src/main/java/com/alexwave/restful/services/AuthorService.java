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
    private final PaperMapper paperMapper;

    @Transactional(readOnly = true)
    public List<AuthorDTO> findAll() {
        List<Author> authors = authorsRepository.findAll();

        if (authors.isEmpty()) {
            throw new AuthorListIsEmptyException();
        } else {
            return authorMapper.authorsToAuthorDTOs(authors);
        }
    }

    @Transactional(readOnly = true)
    public AuthorDTO findById(int id) {
        Optional<Author> optionalAuthor = authorsRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            return authorMapper.authorToAuthorDTO(optionalAuthor.get());
        } else {
            throw new AuthorIdNotFoundException();
        }
    }

    @Transactional
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        author.setName(authorDTO.getName());
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
        Optional<Author> optionalAuthor = authorsRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            authorsRepository.deleteById(optionalAuthor.get().getId());
        } else {
            throw new AuthorIdNotFoundException();
        }

    }

    @Transactional(readOnly = true)
    public List<PaperDTO> findPapersByAuthorId(int id) {
        Optional<Author> optionalAuthor = authorsRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            List<Paper> papers = optionalAuthor.get().getPapers();
            return paperMapper.papersToPaperDTOs(papers);
        } else {
            throw new AuthorIdNotFoundException();
        }
    }
}
