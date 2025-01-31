package com.alexwave.restful.mappers;

import com.alexwave.restful.dto.PaperDTO;
import com.alexwave.restful.entities.Paper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaperMapper {

    PaperDTO paperToPaperDTO(Paper paper);

    Paper paperDTOToPaper(PaperDTO paperDTO);

    List<PaperDTO> papersToPaperDTOs(List<Paper> papers);

    List<Paper> paperDTOsToPapers(List<PaperDTO> paperDTOs);
}
