package com.softuni.bookshopsystem.service;


import com.softuni.bookshopsystem.model.entity.Book;
import com.softuni.bookshopsystem.model.entity.EditionType;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllByAgeRestriction(String ageRestriction);

    List<String> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<String> findAllByPriceLessThanOrPriceGreaterThan(int priceLessThan, int priceGreaterThan);

    List<String> findAllByReleaseDateIsNot(String year);
}
