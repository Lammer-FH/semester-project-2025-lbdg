package com.lbdg.library_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "published_year")
    private Integer publishedYear;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<RatingEntity> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<BookingEntity> bookings = new ArrayList<>();
}
