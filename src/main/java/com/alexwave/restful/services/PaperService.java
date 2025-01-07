package com.alexwave.restful.services;

import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.repositories.PaperRepository;
import com.alexwave.restful.util.my_exceptions.AuthorEmptyListException;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.mapper.AuthorMapper;
import com.alexwave.restful.mapper.PaperMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// FIXME add integration and unit tests

@Service
@RequiredArgsConstructor
public class PaperService {

    private final PaperRepository paperRepository;
    private final PaperMapper paperMapper;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<PaperDTO> findAll() {
        List<Paper> papers = paperRepository.findAll();
        List<PaperDTO> paperDTOS = paperMapper.papersToPaperDTOs(papers);
        if (paperDTOS.isEmpty()) {
            throw new AuthorEmptyListException("Papers do not exist yet!");
        } else {
            return paperDTOS;
        }
    }
    @Transactional(readOnly = true)
    public List<PaperDTO> findAllByAuthorId(int id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            List<Paper> papers = optionalAuthor.get().getPapers();
            List<PaperDTO> paperDTOS = paperMapper.papersToPaperDTOs(papers);
            if (paperDTOS.isEmpty()) {
                throw new AuthorEmptyListException("Papers do not exist yet!");
            } else {
                return paperDTOS;
            }
        } else {
            throw new AuthorIdNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public PaperDTO findById(int id) {
        Optional<Paper> paper = paperRepository.findById(id);

        if (paper.isPresent()) {
            PaperDTO paperDTO = paperMapper.paperToPaperDTO(paper.get()); // для наглядности
            return paperDTO;
        } else {
            throw new AuthorIdNotFoundException();
        }
    }

    @Transactional
    public PaperDTO save(Paper paper) {
        paperRepository.save(paper);
        PaperDTO paperDTO = paperMapper.paperToPaperDTO(paper); // для наглядности
        return paperDTO;
    }

    @Transactional
    public PaperDTO update(int id, Paper paper) {
        Optional<Paper> optionalPaper = paperRepository.findById(id);
        if (optionalPaper.isEmpty()) {
            throw new AuthorIdNotFoundException();
        } else {
            Paper updatedPaper = optionalPaper.get();
            updatedPaper.setTitle(paper.getTitle());
            updatedPaper.setContent(updatedPaper.getContent());
            updatedPaper.setDateForPublishing(updatedPaper.getDateForPublishing());
            paperRepository.save(paper);
            PaperDTO paperDTO = paperMapper.paperToPaperDTO(paper);// для наглядности
            return paperDTO;
        }
    }

    @Transactional
    public void deleteById(int id) {
        paperRepository.deleteById(id);
    }
}
