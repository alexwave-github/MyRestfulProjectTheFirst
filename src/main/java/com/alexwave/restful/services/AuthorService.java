package com.alexwave.restful.services;

import com.alexwave.restful.models.Author;
import com.alexwave.restful.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorsRepository;

    @Autowired
    public AuthorService(AuthorRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }
//---------------------------------------------------Service's methods--------------------------------------------------
    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    public Author findById(int id) {
        Optional<Author> author = authorsRepository.findById(id);
        return author.orElse(null);
    }

    @Transactional
    public Author save(Author author) {
        return authorsRepository.save(author);
    }

    @Transactional
    public Author update(int id, Author author) {
        Optional<Author> authorOptional = authorsRepository.findById(id);
        if (authorOptional.isEmpty()) {
            return null;
        } else {
            Author authorToUpdate = authorOptional.get();
            authorToUpdate.setName(author.getName());
            return authorsRepository.save(authorToUpdate);
        }
    }

    @Transactional
    public void deleteById(int id) {
        authorsRepository.deleteById(id);
    }

//    @Transactional
//    public Author addPaper(int id, Paper paper) {
//        Author author = findById(id);
//        if (author != null) {
//            author.addPaper(paper);
//            return author;
//        }
//        return null;
//    }
//
//    @Transactional
//    public Author removePaper(int authorId, int paperId) {
//        Author author = findById(authorId);
//        if (author != null) {
//            Paper paper = author.getPapers().stream().filter(p -> p.getId() == paperId).findFirst().orElse(null);
//            if (paper != null) {
//                author.removePaper(paper);
//                return save(author);
//            }
//        }
//        return null;
//    }
}
