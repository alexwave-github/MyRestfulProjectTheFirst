package com.alexwave.restful.dto;

import com.alexwave.restful.models.Author;
import lombok.Data;

import java.util.Date;

@Data
public class PaperDTO {

    private String title;
    private String content;
    private Date dateForPublishing;
    private Author author;

}
