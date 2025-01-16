package com.alexwave.restful.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDTO {

    private String title;

    private String content;

    private Instant dateForPublishing;

    private AuthorDTO author;

}
