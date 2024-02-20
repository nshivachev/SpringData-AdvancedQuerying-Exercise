package com.softuni.bookshopsystem.service;


import com.softuni.bookshopsystem.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

//    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<String> findAllByFirstNameEndsWithIgnoreCase(String firstName);
}
