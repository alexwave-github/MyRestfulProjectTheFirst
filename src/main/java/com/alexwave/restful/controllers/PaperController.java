package com.alexwave.restful.controllers;

import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.services.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/papers")
public class PaperController {

    private final PaperService paperService;

    @GetMapping
    public ResponseEntity<List<PaperDTO>> getAllPapers() {
        List<PaperDTO> papers = paperService.findAll();

        return new ResponseEntity<>(papers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaperDTO> getPaperById(@PathVariable(value = "id") int id) {
        PaperDTO paperDTO = paperService.findById(id);

        return new ResponseEntity<>(paperDTO, HttpStatus.OK);
    }

    @PostMapping("/author/{authorId}")
    public ResponseEntity<PaperDTO> createPaper(@RequestBody PaperDTO paperDTO,
                                                @PathVariable(value = "authorId") int authorId) {
        PaperDTO paperDTOtoSave = paperService.save(paperDTO, authorId);

        return new ResponseEntity<>(paperDTOtoSave, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaperDTO> updatePaper(@PathVariable(value = "id") int id, @RequestBody PaperDTO paperDTO) {
        PaperDTO paperToUpdate = paperService.updateById(id,paperDTO);

        return new ResponseEntity<>(paperToUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaper(@PathVariable(value = "id") int id) {
        paperService.deleteById(id);

        return new ResponseEntity<>("Paper with id " + id + " deleted", HttpStatus.OK);
    }
}
