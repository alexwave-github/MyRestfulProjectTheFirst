package com.alexwave.restful.services;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.models.Author;
import com.alexwave.restful.models.Paper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.repositories.PaperRepository;
import com.alexwave.restful.util.exception_handling.EmptyListException;
import com.alexwave.restful.util.exception_handling.IdNotFoundException;
import com.alexwave.restful.util.mapper.AuthorMapper;
import com.alexwave.restful.util.mapper.PaperMapper;
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

    public List<PaperDTO> findAll() {
        List<Paper> papers = paperRepository.findAll();
        List<PaperDTO> paperDTOS = paperMapper.papersToPaperDTOs(papers);
        if (paperDTOS.isEmpty()) {
            throw new EmptyListException("Papers do not exist yet!");
        } else {
            return paperDTOS;
        }
    }

    public List<PaperDTO> findAllByAuthorId(int id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            AuthorDTO authorDTO = authorMapper.authorToAuthorDTO(optionalAuthor.get());
            List<PaperDTO> paperDTOS = authorDTO.getPapers(); // для наглядности
            if (paperDTOS.isEmpty()) {
                throw new EmptyListException("Papers do not exist yet!");
            } else {
                return paperDTOS;
            }
        } else {
            throw new IdNotFoundException("Author not found!");
        }
    }

    public PaperDTO findById(int id) {
        Optional<Paper> paper = paperRepository.findById(id);
        if (paper.isPresent()) {
            PaperDTO paperDTO = paperMapper.paperToPaperDTO(paper.get()); // для наглядности
            return paperDTO;
        } else {
            throw new IdNotFoundException("Paper not found!");
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
            throw new IdNotFoundException("Paper not found!");
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
