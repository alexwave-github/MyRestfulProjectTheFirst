package com.alexwave.restful.util.mapper;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
    AuthorDTO authorToAuthorDTO(Author author);
    List<AuthorDTO> authorsToAuthorDTOs(List<Author> authors);
}
