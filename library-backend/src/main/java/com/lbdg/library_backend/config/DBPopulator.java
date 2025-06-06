package com.lbdg.library_backend.config;

import com.lbdg.library_backend.entities.*;
import com.lbdg.library_backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Configuration
public class DBPopulator {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;

    @Bean
    public CommandLineRunner populateLibrariesAndBooks(LibraryRepository libraryRepository, BookRepository bookRepository, BookingRepository bookingRepository) {
        return args -> {
            if (libraryRepository.count() > 0 || bookRepository.count() > 0 || ratingRepository.count() > 0 || userRepository.count() > 0 || bookingRepository.count() > 0) {
                return;
            }
            // Create users
            UserEntity user1 = userRepository.save(new UserEntity(null, "Franz", "Bauer", "franz.bauer@mail.com", Role.LIBRARIAN, UUID.randomUUID().toString().substring(0,8)));
            UserEntity user2 = userRepository.save(new UserEntity(null, "Anna", "Müller", "anna.mueller@mail.com", Role.LIBRARIAN, UUID.randomUUID().toString().substring(0,8)));
            UserEntity user3 = userRepository.save(new UserEntity(null, "Max", "Mustermann", "max.mustermann@mail.com", Role.STUDENT, UUID.randomUUID().toString().substring(0,8)));
            UserEntity user4 = userRepository.save(new UserEntity(null, "Lea", "Musterfrau", "lea.musterfrau@mail.com", Role.STUDENT, UUID.randomUUID().toString().substring(0,8)));

            LibraryEntity library1 = libraryRepository.save(new LibraryEntity(null, "Technikum Wien", "Höchstädtplatz 6, 1200 Wien"));
            LibraryEntity library2 = libraryRepository.save(new LibraryEntity(null, "Universität Wien", "Universitätsring 1, 1010 Wien"));

            BookEntity book1 = bookRepository.save(new BookEntity(null, library1, "Ulrich Breymann", "C++ programmieren", "918-3-16-148410-0", null, "Carl Hansen", "Ein praxisnahes Lehrbuch, das C++-Grundlagen und fortgeschrittene Themen verständlich vermittelt.", 2023, new ArrayList<>(), new ArrayList<>()));
            BookEntity book2 = bookRepository.save(new BookEntity(null, library2, "Ulrich Breymann", "C++ programmieren", "918-3-16-148410-0", null, "Carl Hansen", "Ein praxisnahes Lehrbuch, das C++-Grundlagen und fortgeschrittene Themen verständlich vermittelt.", 2023, new ArrayList<>(), new ArrayList<>()));

            BookEntity book3 = bookRepository.save(new BookEntity(null, library1, "Daniel Basler", "Neuronale Netze mit C# programmieren", "978-3-55-54435-4", null, "Carl Hansen", "Mit praktischen Beispielen für Machine Learning im Unternehmenseinsatz", 2021, new ArrayList<>(), new ArrayList<>()));

            RatingEntity rating1 = ratingRepository.save(new RatingEntity(null, book1, user3, 5, "Super Buch!\n" +
                    "Sehr verständlich erklärt, tolle Beispiele –\n" +
                    "perfekt, um C++ zu lernen. Absolute\n" +
                    "Empfehlung!"));

            RatingEntity rating2 = ratingRepository.save(new RatingEntity(null, book1, user4, 3, "Ganz okay\n" +
                    "Das Buch erklärt vieles gut, aber manche\n" +
                    "Teile sind schwer verständlich und hätten\n" +
                    "besser erklärt sein können."));

            LocalDate currentDate = LocalDate.now();

            BookingEntity booking1 = bookingRepository.save(new BookingEntity(null, book1, user1, currentDate, currentDate.plusDays(14)));

            BookingEntity booking2 = bookingRepository.save(new BookingEntity(null, book2, user2, currentDate.minusDays(15), currentDate.minusDays(1)));

            book1.getBookings().add(booking1);
            book2.getBookings().add(booking2);
        };


    }
}
