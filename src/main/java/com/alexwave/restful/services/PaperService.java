package com.alexwave.restful.services;

import com.alexwave.restful.models.Paper;
import com.alexwave.restful.repositories.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PaperService {

    private final PaperRepository paperRepository;

    @Autowired
    public PaperService(PaperRepository myRepository) {
        this.paperRepository = myRepository;
    }
//---------------------------------------------------Service's methods--------------------------------------------------
    public List<Paper> findAll(int id) {
        List<Paper> papers = paperRepository.findByAuthorId(id);
        if (papers.isEmpty()) {
            return new ArrayList<>();
        } else {
            return papers;
        }

    }

    public Paper findById(int id) {
        Optional<Paper> paper = paperRepository.findById(id);
        return paper.orElse(null);
    }

    @Transactional
    public void save(Paper paper) {
        paperRepository.save(paper);
    }

    @Transactional
    public Paper update(int id, Paper paper) {
        Optional<Paper> optionalPaper = paperRepository.findById(id);
        if (optionalPaper.isEmpty()) {
            return null;
        } else {
            Paper updatedPaper = optionalPaper.get();
            updatedPaper.setTitle(paper.getTitle());
            updatedPaper.setContent(updatedPaper.getContent());
            updatedPaper.setDateForPublishing(updatedPaper.getDateForPublishing());
            return paperRepository.save(paper);
        }
    }

    @Transactional
    public void deleteById(int id) {
        paperRepository.deleteById(id);
    }
}
