package com.lbdg.library_backend.config;

import com.lbdg.library_backend.entities.BookEntity;
import com.lbdg.library_backend.entities.LibraryEntity;
import com.lbdg.library_backend.repositories.BookRepository;
import com.lbdg.library_backend.repositories.LibraryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DBPopulator {

    @Bean
    public CommandLineRunner populateLibrariesAndBooks(LibraryRepository libraryRepository, BookRepository bookRepository) {
        return args -> {
            List<LibraryEntity> libraryEntities = new ArrayList<>();
            if (!libraryRepository.existsByName("Technikum Wien")) {
                LibraryEntity libraryEntity = libraryRepository.save(LibraryEntity.builder()
                        .name("Technikum Wien")
                        .address("Wien")
                        .build());
                libraryEntities.add(libraryEntity);
            } else {
                LibraryEntity libraryEntity = libraryRepository.findByName("Technikum Wien");
                libraryEntities.add(libraryEntity);
            }
            if (!bookRepository.existsByIsbn("9780451524935")) {
                bookRepository.save(BookEntity.builder()
                        .libraryEntity(libraryEntities.get(0))
                        .author("George Orwell")
                        .title("1984")
                        .isbn("9780451524935")
                        .publisher("Plume")
                        .shortDescription("Dystopian novel about totalitarianism.")
                        .publishedYear(1949)
                        .build());
            }

            if (!bookRepository.existsByIsbn("9780061120084")) {
                bookRepository.save(BookEntity.builder()
                        .libraryEntity(libraryEntities.get(0))
                        .author("Harper Lee")
                        .title("To Kill a Mockingbird")
                        .isbn("9780061120084")
                        .publisher("Harper Perennial")
                        .shortDescription("Classic novel of racism and injustice.")
                        .publishedYear(1960)
                        .build());
            }
        };
    }
}
