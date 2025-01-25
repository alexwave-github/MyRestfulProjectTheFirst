package com.alexwave.restful.services;

import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Author;
import com.alexwave.restful.entities.Paper;
import com.alexwave.restful.mapper.PaperMapper;
import com.alexwave.restful.repositories.AuthorRepository;
import com.alexwave.restful.repositories.PaperRepository;
import com.alexwave.restful.util.my_exceptions.AuthorIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.PaperIdNotFoundException;
import com.alexwave.restful.util.my_exceptions.PaperListIsEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaperService {

    private final PaperRepository paperRepository;
    private final PaperMapper paperMapper;
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public List<PaperDTO> findAll() {
        List<Paper> papers = paperRepository.findAll();
        if (papers.isEmpty()) {
            throw new PaperListIsEmptyException();
        } else {
            return paperMapper.papersToPaperDTOs(papers);
        }
    }

    @Transactional(readOnly = true)
    public PaperDTO findById(int id) {
        Optional<Paper> paper = paperRepository.findById(id);
        if (paper.isPresent()) {
            return paperMapper.paperToPaperDTO(paper.get());
        } else {
            throw new PaperIdNotFoundException();
        }
    }

    @Transactional
    public PaperDTO save(PaperDTO paperDTO, int id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Paper paper = paperMapper.paperDTOToPaper(paperDTO);
            paper.setAuthor(optionalAuthor.get());
            optionalAuthor.get().getPapers().add(paper);
            paperRepository.save(paper);
            authorRepository.save(optionalAuthor.get());

            return paperMapper.paperToPaperDTO(paper);
        } else {
            throw new AuthorIdNotFoundException();
        }
    }

    @Transactional
    public PaperDTO updateById(int id, PaperDTO paperDTO) {
        Optional<Paper> optionalPaper = paperRepository.findById(id);
        if (optionalPaper.isPresent()) {
            Paper paper = optionalPaper.get();
            paper.setTitle(paperMapper.paperDTOToPaper(paperDTO).getTitle());
            paper.setContent(paperMapper.paperDTOToPaper(paperDTO).getContent());
            paper.setDateForPublishing(paperMapper.paperDTOToPaper(paperDTO).getDateForPublishing());
            paperRepository.save(paper);
            return paperMapper.paperToPaperDTO(paper);
        } else {
            throw new PaperIdNotFoundException();
        }
    }

    @Transactional
    public void deleteById(int id) {
        Optional<Paper> optionalPaper = paperRepository.findById(id);
        if (optionalPaper.isPresent()) {
            paperRepository.delete(optionalPaper.get());
        } else {
            throw new PaperIdNotFoundException();
        }
    }
}
