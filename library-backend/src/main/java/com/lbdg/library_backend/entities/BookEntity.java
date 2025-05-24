package com.lbdg.library_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private LibraryEntity libraryEntity;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "published_year")
    private Integer publishedYear;
}
