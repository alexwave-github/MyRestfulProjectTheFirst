package com.alexwave.restful.dto;

import jakarta.validation.constraints.*; // FIXME удали это
import lombok.Data;

@Data
public class AuthorDTO {
    private String name;
}
