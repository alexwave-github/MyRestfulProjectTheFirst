package com.alexwave.restful.entities;

import com.alexwave.restful.util.instantConverter.CustomInstantDeSerializer;
import com.alexwave.restful.util.instantConverter.CustomInstantSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor()
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "papers")
public class Paper {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "date_for_publishing")
    @JsonDeserialize(using = CustomInstantDeSerializer.class)
    @JsonSerialize(using = CustomInstantSerializer.class)
    private Instant dateForPublishing;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Author author;
}
