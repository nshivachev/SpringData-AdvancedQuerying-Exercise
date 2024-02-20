package com.softuni.bookshopsystem;


import com.softuni.bookshopsystem.model.entity.Book;
import com.softuni.bookshopsystem.model.entity.EditionType;
import com.softuni.bookshopsystem.service.AuthorService;
import com.softuni.bookshopsystem.service.BookService;
import com.softuni.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final Scanner scanner;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printAllBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        //Task 1
        printAllBooksByAgeRestriction();

        //Task2
        printAllBooksByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000);

        //Task 3
        printAllBooksByPriceLessThanOrPriceGreaterThan(5, 40);

        //Task 4
        printAllBooksByReleaseDateNot(scanner.nextInt());

        //Task 5
        printAllBooksWithReleaseDateBefore();

        //Task 6
        printAllAuthorsByFirstNameEndsWithIgnoreCase();

        //Task 7
        printAllBooksByTitleContains();

        //Task 8
        printAllBooksByAuthorLastNameStartsWith();

        //Task 9
        printBooksCountByTitleGreaterThan();

        //10
        printBookCopiesByAuthorFullName();
    }

    private void printBookCopiesByAuthorFullName() {
        System.out.println(bookService
                .findCopiesByAuthorFullName(scanner.nextLine()));
    }

    private void printBooksCountByTitleGreaterThan() {
        System.out.println(bookService
                .findCountByTitleGreaterThan(scanner.nextInt()));
    }

    private void printAllBooksByAuthorLastNameStartsWith() {
        bookService
                .findAllByAuthorLastNameStartsWith(scanner.nextLine())
                .forEach(System.out::println);
    }

    private void printAllBooksByTitleContains() {
        bookService
                .findAllByTitleContains(scanner.nextLine())
                .forEach(System.out::println);
    }

    private void printAllAuthorsByFirstNameEndsWithIgnoreCase() {
        authorService
                .findAllByFirstNameEndsWithIgnoreCase(scanner.nextLine())
                .forEach(System.out::println);
    }

    private void printAllBooksWithReleaseDateBefore() {
        bookService
                .findAllWithReleaseDateBefore(scanner.nextLine())
                .forEach(System.out::println);
    }

    private void printAllBooksByReleaseDateNot(int year) {
        bookService
                .findAllByReleaseDateNot(year)
                .forEach(System.out::println);
    }

    private void printAllBooksByPriceLessThanOrPriceGreaterThan(int priceLessThan, int priceGreaterThan) {
        bookService
                .findAllByPriceLessThanOrPriceGreaterThan(priceLessThan, priceGreaterThan)
                .forEach(System.out::println);
    }

    private void printAllBooksByEditionTypeAndCopiesLessThan(EditionType editionType, int copies) {
        bookService
                .findAllByEditionTypeAndCopiesLessThan(editionType, copies)
                .forEach(System.out::println);
    }

    private void printAllBooksByAgeRestriction() {
        bookService
                .findAllByAgeRestriction(scanner.nextLine())
                .forEach(System.out::println);
    }

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

//    private void printAllAuthorsAndNumberOfTheirBooks() {
//        authorService
//                .getAllAuthorsOrderByCountOfTheirBooks()
//                .forEach(System.out::println);
//    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
