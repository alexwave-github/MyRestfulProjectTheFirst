package com.alexwave.restful.mappers;

import com.alexwave.restful.dto.AuthorDTO;
import com.alexwave.restful.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
    AuthorDTO authorToAuthorDTO(Author author);
    Author authorDTOToAuthor(AuthorDTO authorDTO);
    List<AuthorDTO> authorsToAuthorDTOs(List<Author> authors);
    List<Author> authorDTOsToAuthors(List<AuthorDTO> authorDTOs);
}
