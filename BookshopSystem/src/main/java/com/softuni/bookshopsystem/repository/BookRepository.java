package com.softuni.bookshopsystem.repository;

import com.softuni.bookshopsystem.model.entity.AgeRestriction;
import com.softuni.bookshopsystem.model.entity.Book;
import com.softuni.bookshopsystem.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal priceLessThan, BigDecimal priceGreaterThan);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate startDate, LocalDate endDate);

    List<Book> findAllByTitleContainsIgnoreCase(String text);

    List<Book> findAllByAuthorLastNameStartsWith(String prefix);

    @Query("select count(b) from Book b where length(b.title) > :length")
    Integer findCountByTitleGreaterThan(int length);

    List<Book> findAllByAuthorFirstNameAndAuthorLastName(String firstName, String lastName);
}
