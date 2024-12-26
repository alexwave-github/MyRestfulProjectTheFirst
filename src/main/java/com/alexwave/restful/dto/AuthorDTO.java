package com.alexwave.restful.dto;

import com.alexwave.restful.models.Paper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private String name;
    private List<PaperDTO> papers;
}
