package com.softuni.bookshopsystem.service.impl;


import com.softuni.bookshopsystem.model.entity.Author;
import com.softuni.bookshopsystem.repository.AuthorRepository;
import com.softuni.bookshopsystem.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_FILE_PATH = "BookshopSystem/src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(AUTHORS_FILE_PATH))
                .forEach(row -> {
                    String[] fullName = row.split("\\s+");
                    Author author = new Author(fullName[0], fullName[1]);

                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1,
                        authorRepository.count() + 1);

        return authorRepository
                .findById(randomId)
                .orElse(null);
    }

//    @Override
//    public List<String> getAllAuthorsOrderByCountOfTheirBooks() {
//        return authorRepository
//                .findAllByBooksSizeDESC()
//                .stream()
//                .map(author -> String.format("%s %s %d",
//                        author.getFirstName(),
//                        author.getLastName(),
//                        author.getBooks().size()))
//                .collect(Collectors.toList());
//    }
}
