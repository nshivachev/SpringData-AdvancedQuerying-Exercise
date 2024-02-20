package com.softuni.bookshopsystem.service.impl;


import com.softuni.bookshopsystem.model.dto.BookInformation;
import com.softuni.bookshopsystem.model.entity.*;
import com.softuni.bookshopsystem.repository.BookRepository;
import com.softuni.bookshopsystem.service.AuthorService;
import com.softuni.bookshopsystem.service.BookService;
import com.softuni.bookshopsystem.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "BookshopSystem/src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByAgeRestriction(String ageRestriction) {
        return bookRepository
                .findAllByAgeRestriction(AgeRestriction.valueOf(ageRestriction.toUpperCase()))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies) {
        return bookRepository
                .findAllByEditionTypeAndCopiesLessThan(editionType, copies)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findAllByPriceLessThanOrPriceGreaterThan(int priceLessThan, int priceGreaterThan) {
        return bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(new BigDecimal(priceLessThan), new BigDecimal(priceGreaterThan))
                .stream()
                .map(book -> String.format("%s - $%.2f", book.getTitle(), book.getPrice()))
                .toList();
    }

    @Override
    public List<String> findAllByReleaseDateNot(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);

        return bookRepository
                .findAllByReleaseDateBeforeOrReleaseDateAfter(startDate, endDate)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findAllWithReleaseDateBefore(String releaseDate) {
        int[] tokens = Arrays.stream(releaseDate.split("-")).mapToInt(Integer::parseInt).toArray();
        LocalDate localDate = LocalDate.of(tokens[2], tokens[1], tokens[1]);

        return bookRepository
                .findAllByReleaseDateBefore(localDate)
                .stream()
                .map(book -> String.format("%s %s %.2f",
                        book.getTitle(),
                        book.getEditionType(),
                        book.getPrice()))
                .toList();
    }

    @Override
    public List<String> findAllByTitleContains(String text) {
        return bookRepository
                .findAllByTitleContainsIgnoreCase(text)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findAllByAuthorLastNameStartsWith(String prefix) {
        return bookRepository
                .findAllByAuthorLastNameStartsWith(prefix)
                .stream()
                .map(book -> String.format("%s (%s %s)",
                        book.getTitle(),
                        book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .toList();
    }

    @Override
    public Integer findCountByTitleGreaterThan(int length) {
        return bookRepository
                .findCountByTitleGreaterThan(length);
    }

    @Override
    public Integer findCopiesByAuthorFullName(String authorFullName) {
        String[] tokens = authorFullName.split("\\s+");
        return bookRepository
                .findAllByAuthorFirstNameAndAuthorLastName(tokens[0], tokens[1])
                .stream()
                .mapToInt(Book::getCopies).sum();
    }

    @Override
    public BookInformation findByTitle(String title) {
        return bookRepository
                .findByTitle(title)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
        public int increaseCopiesWithReleaseDateAfter(String dateAfter, String increasedCopies) {
        return bookRepository
                .increaseCopiesWithReleaseDateAfter(
                        LocalDate.parse(dateAfter, DateTimeFormatter.ofPattern("dd MMM yyyy")),
                        Integer.parseInt(increasedCopies))
                * Integer.parseInt(increasedCopies);
    }

    @Override
    public int deleteByCopiesLessThan(int copies) {
        return bookRepository.deleteByCopiesLessThan(copies);
    }

    @Override
    public String getCountByAuthorFullName(String fullName) {
        String[] tokens = fullName.split("\\s+");
        return String.format("%s has written %d books",
                fullName,
                bookRepository.getCountByAuthorName(tokens[0], tokens[1]));
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);
    }
}
