package com.alexwave.restful.mappers;

import com.alexwave.AbstractTestClass;
import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Paper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaperMapperTest extends AbstractTestClass {

    @Autowired
    private PaperMapper paperMapper;

    @Test
    void paperToPaperDTO() {
        Paper paper = Instancio.of(Paper.class)
                .ignore(field(Paper::getId)).ignore(field(Paper::getAuthor)).create();

        PaperDTO paperDTO = paperMapper.paperToPaperDTO(paper);

        assertNotNull(paperDTO);
        assertEquals(paper.getTitle(), paperDTO.getTitle());
        assertEquals(paper.getContent(), paperDTO.getContent());
        assertEquals(paper.getDateForPublishing(), paperDTO.getDateForPublishing());
    }

    @Test
    void paperDTOToPaper() {
        PaperDTO paperDTO = Instancio.create(PaperDTO.class);

        Paper paper = paperMapper.paperDTOToPaper(paperDTO);

        assertNotNull(paper);
        assertEquals(paper.getTitle(), paperDTO.getTitle());
        assertEquals(paper.getContent(), paperDTO.getContent());
        assertEquals(paper.getDateForPublishing(), paperDTO.getDateForPublishing());
    }

    @Test
    void papersToPaperDTOs() {
        List<Paper> papers = Instancio.createList(Paper.class);

        List<PaperDTO> paperDTOs = paperMapper.papersToPaperDTOs(papers);

        assertNotNull(paperDTOs);
        assertEquals(papers.getFirst().getTitle(), paperDTOs.getFirst().getTitle());
        assertEquals(papers.getFirst().getContent(), paperDTOs.getFirst().getContent());
        assertEquals(papers.getFirst().getDateForPublishing(), paperDTOs.getFirst().getDateForPublishing());
    }

    @Test
    void paperDTOsToPapers() {
        List<PaperDTO> paperDTOs = Instancio.createList(PaperDTO.class);

        List<Paper> papers = paperMapper.paperDTOsToPapers(paperDTOs);

        assertNotNull(papers);
        assertEquals(papers.getFirst().getTitle(), paperDTOs.getFirst().getTitle());
        assertEquals(papers.getFirst().getContent(), paperDTOs.getFirst().getContent());
        assertEquals(papers.getFirst().getDateForPublishing(), paperDTOs.getFirst().getDateForPublishing());
    }
}