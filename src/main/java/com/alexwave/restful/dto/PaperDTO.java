package com.alexwave.restful.dto;

import com.alexwave.restful.models.Author;

import com.alexwave.restful.util.instantConverter.CustomInstantDeSerializer;
import com.alexwave.restful.util.instantConverter.CustomInstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDTO {

    private String title;

    private String content;

    @JsonSerialize(using = CustomInstantSerializer.class)
    @JsonDeserialize(using = CustomInstantDeSerializer.class)
    private Instant dateForPublishing;

    private AuthorDTO author;

}
