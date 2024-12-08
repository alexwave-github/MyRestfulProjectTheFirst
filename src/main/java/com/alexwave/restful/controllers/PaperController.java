package com.alexwave.restful.controllers;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.models.Author;
import com.alexwave.restful.models.Paper;
import com.alexwave.restful.services.AuthorService;
import com.alexwave.restful.services.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PaperController {

    private final PaperService paperService;
    private final AuthorService authorService;

    @Autowired
    public PaperController(PaperService myService, AuthorService authorService) {
        this.paperService = myService;
        this.authorService = authorService;
    }

    @GetMapping("/authors/{authorId}/papers")
    public ResponseEntity<List<Paper>> getPapersByAuthorId(@PathVariable(value = "authorId") int authorId) {
        List<Paper> papers = paperService.findAll(authorId);
        if (papers.isEmpty()) {
            return new ResponseEntity<>(papers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(papers, HttpStatus.OK);
        }
    }

    @GetMapping("/papers/{id}")
    public ResponseEntity<Paper> getPaperById(@PathVariable(value = "id") int id) {
        Paper paper = paperService.findById(id);
        if (paper == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(paper, HttpStatus.OK);
        }
    }

    @PostMapping("/authors/{authorId}/papers")
    public ResponseEntity<Paper> createPaper(@RequestBody PaperDTO paperDTO, @PathVariable(value = "authorId") int authorId) {
        Author author = authorService.findById(authorId);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Paper paper = convertToPaper(paperDTO);
        paper.setAuthor(author);
        paperService.save(paper);
        return new ResponseEntity<>(paper, HttpStatus.CREATED);
    }

    @PutMapping("/papers/{id}")
    public ResponseEntity<Paper> updatePaper(@PathVariable(value = "id") int id, @RequestBody PaperDTO paperDTO) {
        Paper paper = paperService.update(id,convertToPaper(paperDTO));
        if (paper == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(paper, HttpStatus.OK);
        }
    }

    @DeleteMapping("/papers/{id}")
    public ResponseEntity<String> deletePaper(@PathVariable(value = "id") int id) {
        paperService.deleteById(id);
        return new ResponseEntity<>("Paper with id " + id + " deleted", HttpStatus.OK);
    }

    private Paper convertToPaper(PaperDTO paperDTO) {
        Paper paper = new Paper();
        paper.setTitle(paperDTO.getTitle());
        paper.setContent(paperDTO.getContent());
        paper.setDateForPublishing(paperDTO.getDateForPublishing());
        return paper;
    }

}
